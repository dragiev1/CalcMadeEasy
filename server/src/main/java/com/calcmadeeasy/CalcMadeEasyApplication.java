package com.calcmadeeasy;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problem.Problem;
import com.calcmadeeasy.models.Problem.ProblemSolutionType;
import com.calcmadeeasy.models.Problem.ProblemType;

@SpringBootApplication
public class CalcMadeEasyApplication {
    public static void main(String[] args) {
        // SpringApplication.run(CalcMadeEasyApplication.class, args);

        Page page = Page.builder()
                .content("Integration by Parts Intro")
                .build();

        // Create some problems
        Problem p1 = Problem.builder()
                .description("Compute ∫ x * e^x dx")
                .solutionType(ProblemSolutionType.EXPRESSION)
                .solution("xe^x + e^x + c")
                .points(3)
                .isChallenge(true)
                .topics("Integral", "By Parts Method", "Indefinite Integral")
                .build();

        Problem p2 = Problem.builder()
                .description("Solve ∫ cos^2(x) dx")
                .solutionType(ProblemSolutionType.EXPRESSION)
                .solution("xe^x + e^x + c")
                .points(3)
                .isChallenge(true)
                .topics("Integral", "Trig Identity", "Indefinite Integral")
                .build();

        page.setProblem(p1, ProblemType.EXERCISE);
        page.setProblem(p2, ProblemType.EXERCISE);

        

    }
}