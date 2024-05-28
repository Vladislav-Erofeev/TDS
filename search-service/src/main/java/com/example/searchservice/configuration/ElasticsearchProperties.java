package com.example.searchservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("elasticsearch")
public class ElasticsearchProperties {
    private String host;
    private Integer port;
    private String itemIndex;
    private Integer searchLimit;
    private String combinedField;
    private String nameField;
}
