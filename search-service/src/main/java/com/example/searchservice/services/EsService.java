package com.example.searchservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EsService {
    private final static String INDEX_NAME = "geodata";
    private final RestHighLevelClient highLevelClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void save(String id, String title, String body) throws IOException {
        Geodata geodata = new Geodata();
        geodata.setBody(body);
        geodata.setTitle(title);

        IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
        indexRequest.id(id);
        indexRequest.source(objectMapper.writeValueAsString(geodata), XContentType.JSON);

        highLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    public List<Geodata> search(String title) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.combinedFieldsQuery(title, "title", "body")
                .operator(Operator.AND));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<Geodata> res = new LinkedList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> data = hit.getSourceAsMap();
            System.out.println(hit.getId());

            Geodata geodata = new Geodata();
            geodata.setTitle((String) data.get("title"));
            geodata.setBody((String) data.get("body"));
            res.add(geodata);
        }
        return res;
    }

    public void deleteById(String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX_NAME, id);
        highLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
    }

    public void updateById(String id, String title, String body) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME, id);
        Geodata geodata = new Geodata();
        geodata.setBody(body);
        geodata.setTitle(title);
        updateRequest.doc(objectMapper.writeValueAsString(geodata), XContentType.JSON);
        highLevelClient.update(updateRequest, RequestOptions.DEFAULT);
    }

    @Getter
    @Setter
    public static class Geodata {
        private String title;
        private String body;
    }
}
