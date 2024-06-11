package com.example.loadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LoadServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoadServiceApplication.class, args);
    }

}
