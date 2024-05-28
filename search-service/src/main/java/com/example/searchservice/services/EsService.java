package com.example.searchservice.services;

import com.example.searchservice.configuration.ElasticsearchProperties;
import com.example.searchservice.messages.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EsService {
    private final RestHighLevelClient highLevelClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ElasticsearchProperties elasticsearchProperties;

    public void save(String id, Item item) throws IOException {
        IndexRequest indexRequest = new IndexRequest(elasticsearchProperties.getItemIndex());
        indexRequest.id(id);
        indexRequest.source(objectMapper.writeValueAsString(item), XContentType.JSON);
        highLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    public void deleteById(String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(elasticsearchProperties.getItemIndex(), id);
        highLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
    }

    public void updateById(String id, Item item) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(elasticsearchProperties.getItemIndex(), id);
        updateRequest.doc(objectMapper.writeValueAsString(item), XContentType.JSON);
        highLevelClient.update(updateRequest, RequestOptions.DEFAULT);
    }

    public List<Item> search(String query) throws IOException {
        SearchRequest searchRequest = new SearchRequest(elasticsearchProperties.getItemIndex());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders
                        .matchQuery(elasticsearchProperties.getCombinedField(), query)
                        .operator(Operator.AND)
                        .fuzziness(Fuzziness.AUTO))
                .should(
                        QueryBuilders.matchQuery(elasticsearchProperties.getNameField(), query)
                                .operator(Operator.AND)
                                .fuzziness(Fuzziness.AUTO)
                )
        );
        searchSourceBuilder.size(elasticsearchProperties.getSearchLimit());
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return Arrays.stream(response.getHits().getHits()).map(this::mapSourceToItem).toList();
    }

    public List<Item> searchWithFilters(String query, List<Long> codes) throws IOException {
        SearchRequest searchRequest = new SearchRequest(elasticsearchProperties.getItemIndex());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders
                        .matchQuery(elasticsearchProperties.getCombinedField(), query)
                        .operator(Operator.AND)
                        .fuzziness(Fuzziness.AUTO))
                .should(
                        QueryBuilders.matchQuery(elasticsearchProperties.getNameField(), query)
                                .operator(Operator.AND)
                                .fuzziness(Fuzziness.AUTO)
                )
                .filter(QueryBuilders.termsQuery("code_id", codes))
        );
        searchSourceBuilder.size(elasticsearchProperties.getSearchLimit());
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return Arrays.stream(response.getHits().getHits()).map(this::mapSourceToItem).toList();
    }

    private Item mapSourceToItem(SearchHit hit) {
        Map<String, Object> data = hit.getSourceAsMap();
        Item item = new Item();
        item.setId(Long.valueOf(data.getOrDefault("id", null).toString()));
        item.setCode_id(Long.valueOf(data.getOrDefault("code_id", null).toString()));
        item.setName((String) data.getOrDefault("name", null));
        item.setAddr_country((String) data.getOrDefault("addr_country", null));
        item.setAddr_city((String) data.getOrDefault("addr_city", null));
        item.setAddr_street((String) data.getOrDefault("addr_street", null));
        item.setAddr_housenumber((String) data.getOrDefault("addr_housenumber", null));
        return item;
    }
}
