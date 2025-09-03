package server;

import java.util.List;

import server.models.Pages.Page;
import server.models.Problem.Problem;
import server.models.Problem.ProblemType;

class Main {
  public static void main(String[] args) {
    // Test to see if Page.java is working properly.
    Problem problem1 = Problem.builder()
      .description("Derivate 2x.")
      .type(ProblemType.exercise)
      .points(1)
      .solutionPath("Solutions/Derivatives/2.md")
      .isChallenge(false)
      .topics("Derivatives", "Power Rule")
      .build();

    Problem problem2 = Problem.builder()
      .description("Integrate 2xdx.")
      .type(ProblemType.exercise)
      .points(2)
      .solutionPath("Solutions/Derivatives/3.md")
      .isChallenge(false)
      .topics("Integrals", "Power Rule")
      .build();

    Problem problem3 = Problem.builder()
      .description("Integrate 2x^2dx.")
      .type(ProblemType.exercise)
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

    List<Problem> hardHWExercises = page1.getProblemsBy(p -> p.getTopics(), "Derivatives", page1.getHomework());
    System.out.println(hardHWExercises.toString());
    
  }
}