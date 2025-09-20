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
import com.calcmadeeasy.models.Users.UserProgress;
import com.calcmadeeasy.repository.CourseRepo;
import com.calcmadeeasy.repository.TagRepo;
import com.calcmadeeasy.repository.UserRepo;
import com.calcmadeeasy.services.TagServices;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class CalcMadeEasyApplication {

        public static void main(String[] args) {
                SpringApplication.run(CalcMadeEasyApplication.class, args);

        }

        @Bean
        CommandLineRunner testSave(UserRepo userRepo, CourseRepo courseRepo, TagRepo tagRepo, TagServices tagServices) {
                return args -> {
                        Tag tag1 = tagServices.getOrCreateTag("Integral", 0.5);
                        Tag tag2 = tagServices.getOrCreateTag("By Parts Method", 0.7);
                        Tag tag3 = tagServices.getOrCreateTag("Trig Identity", 0.4);
                        Tag tag4 = tagServices.getOrCreateTag("Indefinite Integral", 0.6);

                        // Create some problems
                        Problem p1 = Problem.builder()
                                        .description("Compute ∫ x * e^x dx")
                                        .solutionType(ProblemSolutionType.EXPRESSION)
                                        .solution("xe^x + e^x + c")
                                        .points(3)
                                        .isChallenge(true)
                                        .tags(tag1, tag2, tag3)
                                        .build();

                        Problem p2 = Problem.builder()
                                        .description("Solve ∫ cos^2(x) dx")
                                        .solutionType(ProblemSolutionType.EXPRESSION)
                                        .solution("xe^x + e^x + c")
                                        .points(3)
                                        .isChallenge(true)
                                        .tags(tag1, tag2, tag4)
                                        .build();

                        Page page = Page.builder()
                                        .content("Integration by Parts Intro")
                                        .build();

                        page.setProblem(p1, ProblemType.EXERCISE);
                        page.setProblem(p2, ProblemType.EXERCISE);

                        Section section = Section.builder()
                                        .description("We will learn by parts method in this section.")
                                        .title("By Parts Method")
                                        .pages(page)
                                        .build();

                        Chapter chapter = Chapter.builder()
                                        .description("In this chapter, we will learn some of the most powerful fundamental integration methods.")
                                        .title("Integration Methods")
                                        .sections(section)
                                        .build();

                        Course course = Course.builder()
                                        .title("Calculus II")
                                        .chapters(chapter)
                                        .description("In this course, we will learn the continous fundamentals of Calculus.")
                                        .build();

                        User user = User.builder()
                                        .courses(course)
                                        .email("dragievs2020@gmail.com")
                                        .firstName("Stiviyan")
                                        .lastName("Dragiev")
                                        .profilePicUrl("./server/")
                                        .build();

                        
                        //user.setUserProgress(page, p1);

                        courseRepo.save(course);
                        //userRepo.save(user);

                };
        }
}