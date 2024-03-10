package vlad.erofeev.layerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LayerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LayerServiceApplication.class, args);
    }

}