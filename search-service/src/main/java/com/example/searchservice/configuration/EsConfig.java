package com.example.searchservice.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EsConfig {
    private final ElasticsearchProperties elasticsearchProperties;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(elasticsearchProperties.getHost(),
                elasticsearchProperties.getPort(), "http")));
    }
}
