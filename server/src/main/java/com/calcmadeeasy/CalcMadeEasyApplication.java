package com.calcmadeeasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.calcmadeeasy.repository.CourseRepo;
import com.calcmadeeasy.repository.PageRepo;
import com.calcmadeeasy.repository.ProblemRepo;
import com.calcmadeeasy.repository.UserRepo;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class CalcMadeEasyApplication {

        public static void main(String[] args) {
                SpringApplication.run(CalcMadeEasyApplication.class, args);

        }

        @Bean
        CommandLineRunner testSave(UserRepo userRepo, CourseRepo courseRepo, PageRepo pageRepo, ProblemRepo problemRepo) {
                return args -> {
                 
                };
        }
}