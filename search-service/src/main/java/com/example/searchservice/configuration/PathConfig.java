package com.example.searchservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "static")
@Getter
@Setter
public class PathConfig {
    private String path;
    private String geocodingSource;
    private String geocodingReport;
}
