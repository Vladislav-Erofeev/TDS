package com.example.geodata.configuration;

import com.example.geodata.services.CentroidService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class GrpcConfiguration {

    @Bean
    public CommandLineRunner commandLineRunner(WebServerApplicationContext context, CentroidService service) {
        return arg -> {
            Thread thread = new Thread(() -> {
                log.info("Start grpc server on port {}", context.getWebServer().getPort() + 10);
                Server server = ServerBuilder.forPort(context.getWebServer().getPort() + 10).addService(service).build();
                try {
                    server.start();
                    server.awaitTermination();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    log.info("Stop grpc server");
                }
            });
            thread.start();
        };
    }

}
