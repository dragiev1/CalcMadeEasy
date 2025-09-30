package com.calcmadeeasy.config;

import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class DotenvConfig {
  static {
    try {
      Dotenv dotenv = Dotenv.configure()
                            .directory("./server/")
                            .ignoreIfMalformed()
                            .ignoreIfMissing()
                            .load();

      // dotenv.entries().forEach(entry -> {
      //   System.out.println(entry.getKey() + ", " + entry.getValue());
      // });
      
      dotenv.entries().forEach(entry -> {
        System.setProperty(entry.getKey(), entry.getValue());
      });

      System.out.println("-----.env loaded successfully-----");
    } catch (Exception e) {
      System.err.println("!!!!! WARNING: Could not load .env file !!!!!" + e.getMessage());
    }
  }
}
