package com.calcmadeeasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problem.Problem;
import com.calcmadeeasy.models.Problem.ProblemSolutionType;
import com.calcmadeeasy.models.Problem.ProblemType;
import com.calcmadeeasy.models.Tags.Tag;


@SpringBootApplication
public class CalcMadeEasyApplication {

        public static void main(String[] args) {
                SpringApplication.run(CalcMadeEasyApplication.class, args);

                // Page page = Page.builder()
                // .content("Integration by Parts Intro")
                // .build();

                // Tag tag1 = new Tag("Integral", 0.5);
                // Tag tag2 = new Tag("By Parts Method", 0.7);
                // Tag tag3 = new Tag("Trig Identity", 0.4);
                // Tag tag4 = new Tag("Indefinite Integral", 0.6);

                // // Create some problems
                // Problem p1 = Problem.builder()
                // .description("Compute ∫ x * e^x dx")
                // .solutionType(ProblemSolutionType.EXPRESSION)
                // .solution("xe^x + e^x + c")
                // .points(3)
                // .isChallenge(true)
                // .tags(tag1, tag2, tag3)
                // .build();

                // Problem p2 = Problem.builder()
                // .description("Solve ∫ cos^2(x) dx")
                // .solutionType(ProblemSolutionType.EXPRESSION)
                // .solution("xe^x + e^x + c")
                // .points(3)
                // .isChallenge(true)
                // .tags(tag1,tag2,tag4)
                // .build();

                // page.setProblem(p1, ProblemType.EXERCISE);
                // page.setProblem(p2, ProblemType.EXERCISE);
        }
}