package com.calcmadeeasy.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println("=== DotenvEnvironmentPostProcessor is running ===");
        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory("./") 
                    .ignoreIfMalformed()
                    .ignoreIfMissing()
                    .load();

            Map<String, Object> dotenvProperties = new HashMap<>();
            dotenv.entries().forEach(entry -> {
                dotenvProperties.put(entry.getKey(), entry.getValue());
            });

            environment.getPropertySources().addLast(
                    new MapPropertySource("dotenv", dotenvProperties)
            );
        } catch (Exception e) {
            // Log warning but don't fail startup - allows for production deployment
            System.err.println("Warning: Could not load .env file: " + e.getMessage());
        }
    }
}