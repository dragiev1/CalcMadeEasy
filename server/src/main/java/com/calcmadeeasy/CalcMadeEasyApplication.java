package com.calcmadeeasy;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalcMadeEasyApplication {

    static {
        // Load environment variables from .env file
        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory("./server/")
                    .ignoreIfMalformed()
                    .ignoreIfMissing()
                    .load();
            
            // Set as system properties for Spring Boot to use
            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
            });
            
        } catch (Exception e) {
            System.err.println("Warning: Could not load .env file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(CalcMadeEasyApplication.class, args);
    }
}