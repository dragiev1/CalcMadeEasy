package server;

import java.util.List;
import server.models.Problem.Problem;
import server.models.Problem.ProblemType;

class Main {
  public static void main(String[] args) {
    Problem p = Problem.builder()
        .description("Find the derivative of 2x")
        .type(ProblemType.exercise)
        .solutionPath("solutions/derivatives/2.md")
        .isChallenge(false)
        .topics(List.of("derivatives", "power rule"))
        .points(1)
        .build();

    System.out.println(p);
    System.out.println("After Edits: ");
    p.updateDifficulty(true);
    p.newTopicsList(List.of("eval", "test1", "test2"));
    p.addTopic("evaluation");
    p.updatePoints(2);
    p.moveSolutionPath("Solution/Derivatives/2.md");
    System.out.println(p);
  }
}