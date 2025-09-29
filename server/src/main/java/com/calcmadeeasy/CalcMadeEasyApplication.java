package com.calcmadeeasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problem.Problem;
import com.calcmadeeasy.models.Problem.ProblemSolutionType;
import com.calcmadeeasy.models.Problem.ProblemType;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.models.Tags.Tag;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.repository.CourseRepo;
import com.calcmadeeasy.repository.PageRepo;
import com.calcmadeeasy.repository.ProblemRepo;
import com.calcmadeeasy.repository.TagRepo;
import com.calcmadeeasy.repository.UserRepo;
import com.calcmadeeasy.services.TagServices;

import java.util.List;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class CalcMadeEasyApplication {

        public static void main(String[] args) {
                SpringApplication.run(CalcMadeEasyApplication.class, args);

        }

        @Bean
        CommandLineRunner testSave(UserRepo userRepo, CourseRepo courseRepo, PageRepo pageRepo, ProblemRepo problemRepo) {
                return args -> {
                        
                        // List<Page> pages = pageRepo.findAll();
                                  
                        
                        // System.out.println(pages);
                        
                        // System.out.println(problemRepo.findByPageProblemsByPageId(pages[0].id));

                };
        }
}