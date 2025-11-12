package com.calcmadeeasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.calcmadeeasy.repository.CourseRepo;
import com.calcmadeeasy.repository.PageRepo;
import com.calcmadeeasy.repository.ProblemRepo;
import com.calcmadeeasy.repository.TagRepo;
import com.calcmadeeasy.repository.UserRepo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class CalcMadeEasyApplication {

        public static void main(String[] args) {
                SpringApplication.run(CalcMadeEasyApplication.class, args);

        }

        @Bean
        CommandLineRunner testDatasource(UserRepo userRepo, CourseRepo courseRepo, PageRepo pageRepo, ProblemRepo problemRepo, TagRepo tagRepo, DataSource datasource) {
                return args -> {
                        System.out.println("---- Testing Database Connection ----"); 

                        try (Connection conn = datasource.getConnection()) {
                                 System.out.println("Connected to database");
                                 System.out.println("Database Product Name: " + conn.getMetaData().getDatabaseProductName());
                                 System.out.println("Database Product Version: " + conn.getMetaData().getDatabaseProductVersion());
                        } catch (SQLException e) {
                                System.err.println("Failed to connect to database!");
                                e.printStackTrace();
                        }

                        System.out.println("---- End of Test ----");
                };
        }

        @Bean
        CommandLineRunner testSave(UserRepo userRepo, CourseRepo courseRepo, PageRepo pageRepo, ProblemRepo problemRepo, TagRepo tagRepo) {
                return args -> {
                 
                };
        }
}