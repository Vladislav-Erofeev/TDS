package vlad.erofeev.layerservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LayerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LayerServiceApplication.class, args);
    }
}
