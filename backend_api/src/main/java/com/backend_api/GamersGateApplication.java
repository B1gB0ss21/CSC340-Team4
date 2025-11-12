package com.backend_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.backend_api")
@EnableJpaRepositories(basePackages = "com.backend_api")
@EntityScan(basePackages = "com.backend_api")
public class GamersGateApplication {
    public static void main(String[] args) {
        SpringApplication.run(GamersGateApplication.class, args);
    }
}