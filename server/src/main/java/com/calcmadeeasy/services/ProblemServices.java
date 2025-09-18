package com.calcmadeeasy.services;

import java.util.List;
import java.util.UUID;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

import com.calcmadeeasy.models.Problem.Problem;
import com.calcmadeeasy.models.Problem.ProblemSolutionType;
import com.calcmadeeasy.repository.ProblemRepo;

// TODO: TEST THIS!
public class ProblemServices {

  private final ProblemRepo repo;

  public ProblemServices(ProblemRepo repo) {
    this.repo = repo;
  }

  public Problem saveProblem(Problem problem) {
    return repo.save(problem);
  }

  public List<Problem> getAllProblem() {
    return repo.findAll();
  }


  public Problem getProblem(UUID id) {
    return repo.findById(id).orElse(null);
  }


  public boolean verifySolution(Problem problem, String userSolution) {
    ProblemSolutionType type = problem.getType();

    switch (type) {
      case NUMERICAL:

        return VerifyNumericalSolution(userSolution, problem.getSolution());

      case EXPRESSION:

        return VerifyExpressionSolution(userSolution, problem.getSolution());

      default:
        throw new IllegalArgumentException("Unknown problem solution type.");
    }
  }

  // For verifying basic numerical answers; simple and fast.
  public boolean VerifyNumericalSolution(String userSolution, String problemSolution) {
    try {
      double solution = Double.parseDouble(userSolution.trim());
      double correctSolution = Double.parseDouble(problemSolution.trim());
      return Math.abs(solution - correctSolution) < 1e-6;

    } catch (NumberFormatException e) {
      return false;
    }
  }

  // For verifying expression type solutions through symja.
  public boolean VerifyExpressionSolution(String userSolution, String problemSolution) {
    try {
      // Object from symja for verifying expression equivalence.
      ExprEvaluator util = new ExprEvaluator();
      IExpr userExpr = util.parse(userSolution.trim());
      IExpr correctExpr = util.parse(problemSolution.trim());

      return correctExpr.equals(userExpr);

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}