package com.example.altech;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Electronic Store API",
                version = "1.0",
                description = "API documentation for the electronic store")
)
@SpringBootApplication
public class AltechApplication {

    public static void main(String[] args) {
        SpringApplication.run(AltechApplication.class, args);
    }

}
