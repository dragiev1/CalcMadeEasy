package services;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

import models.Problem.Problem;
import models.Problem.ProblemSolutionType;

public class ProblemServices {

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