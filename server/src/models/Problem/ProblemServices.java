package server.src.models.Problem;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

public class ProblemServices {

  public static boolean verifySolution(Problem problem, String userSolution) {
    ProblemSolutionType type = problem.getType();

    switch (type) {
      case NUMERICAL:
        
        return VerifyNumericalSolution(userSolution, problem.getSolution());

      case EXPRESSION:
        // TODO: Import Symja for checking expressions. 
        return true;

      default:
        throw new IllegalArgumentException("Unknown problem solution type.");
    }    
  }

  public static boolean VerifyNumericalSolution(String userSolution, String problemSolution) {
    try {
      double solution = Double.parseDouble(userSolution.trim());
      double correctSolution = Double.parseDouble(problemSolution.trim());
      return Math.abs(solution - correctSolution) < 1e-6;

    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static boolean VerifyExpressionSolution(String userSolution, String problemSolution) {
    try {
      ExprEvaluator util = new ExprEvaluator(false, 100);
      IExpr userExpr = util.parse(userSolution);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}