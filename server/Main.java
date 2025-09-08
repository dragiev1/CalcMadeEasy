package server;

import server.models.Pages.Page;
import server.models.Problem.Problem;
import server.models.Problem.ProblemSolutionType;

class Main {
  
  public static void main(String[] args) {
    // Test to see if Page.java is working properly.
    Problem problem1 = Problem.builder()
        .description("Derivate 2x.")
        .type(ProblemSolutionType.NUMERICAL)
        .points(1)
        .solutionPath("Solutions/Derivatives/2.md")
        .isChallenge(false)
        .topics("Derivatives", "Power Rule")
        .build();

    Problem problem2 = Problem.builder()
        .description("Integrate 2xdx.")
        .type(ProblemSolutionType.NUMERICAL)
        .points(2)
        .solutionPath("Solutions/Derivatives/3.md")
        .isChallenge(false)
        .topics("Integrals", "Power Rule")
        .build();

    Problem problem3 = Problem.builder()
        .description("Integrate 2x^2dx.")
        .type(ProblemSolutionType.EXPRESSION)
        .points(3)
        .solutionPath("Solutions/Derivatives/2.md")
        .isChallenge(true)
        .topics("Integrals", "Power Rule")
        .build();

    Page page1 = Page.builder()
        .content("MATH STUFF HERE HEHEHHEHEHEHEHEHEHEHEHEE")
        .exercises(problem1, problem2)
        .homework(problem1, problem2, problem3)
        .build();

    page1.removeHomework(problem3.getId());
    System.out.println(page1.toString());
  }
}