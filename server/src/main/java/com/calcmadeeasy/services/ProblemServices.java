package com.calcmadeeasy.services;

import java.util.List;
import java.util.UUID;
import java.util.Arrays;


import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

import com.calcmadeeasy.models.Problem.Problem;
import com.calcmadeeasy.models.Problem.ProblemSolutionType;
import com.calcmadeeasy.models.Tags.Tag;
import com.calcmadeeasy.repository.ProblemRepo;

import org.springframework.stereotype.Service;

@Service
public class ProblemServices {

  private final ProblemRepo repo;

  public ProblemServices(ProblemRepo repo) {
    this.repo = repo;
  }

  // ==================== CREATE ====================

  public Problem createProblem(Problem problem) {
    return repo.save(problem);
  }

  public List<Problem> createProblems(Problem... problems) {
    return repo.saveAll(Arrays.asList(problems));
  }

  public List<Problem> createProblems(List<Problem> problems) {
    return repo.saveAll(problems);
  }

  public Problem addTag(Problem problem, Tag tag) {
    problem.getTags().add(tag);
    return repo.save(problem);
  }

  // ==================== READ ====================

  public List<Problem> getAllProblems() {
    return repo.findAll();
  }

  public Problem getProblemById(UUID id) {
    return repo.findById(id).orElse(null);
  }

  public boolean verifySolution(Problem problem, String userSolution) {
    ProblemSolutionType type = problem.getSolutionType();

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

  public boolean exists(UUID problemId) {
    return repo.existsById(problemId);
  }

  // ==================== UPDATE ====================

  public void updateDescription(UUID problemId, String newDescription) {
    Problem problem = getProblemById(problemId);
    problem.setDescription(newDescription);
    repo.save(problem);
  }

  public void updateSolution(UUID problemId, String newSolution) {
    Problem problem = getProblemById(problemId);
    problem.setSolution(newSolution);
    repo.save(problem);
  }

  public void updateSolutionType(UUID problemId, ProblemSolutionType type) {
    if (type != ProblemSolutionType.NUMERICAL && type != ProblemSolutionType.EXPRESSION)
      throw new IllegalArgumentException("Type " + type + " does not exist as a ProblemSolutionType enum");
    Problem problem = getProblemById(problemId);
    problem.setSolutionType(type);
    repo.save(problem);
  }

  public void updateIsChallenge(UUID problemId, boolean isChallenge) {
    Problem problem = getProblemById(problemId);
    problem.setIsChallenge(isChallenge);
    repo.save(problem);
  }

  public void updatePoints(UUID problemId, int newPoints) {
    Problem problem = getProblemById(problemId);
    problem.setPoints(newPoints);
    repo.save(problem);
  }

  // ==================== DELETE ====================

  public void deleteProblem(UUID problemId) {
    if (!exists(problemId))
      throw new IllegalArgumentException("Problem to be deleted does not exist");
    repo.deleteById(problemId);
  }

  public void deleteTagFromProblem(UUID tagId, UUID problemId) {
    if (!exists(problemId))
      throw new IllegalArgumentException("Problem to be deleted does not exist");
    Problem p = getProblemById(problemId);
    boolean removed = p.getTags().removeIf(t -> t.getId().equals(tagId));
    if (!removed)
      throw new IllegalArgumentException("Problem was found but tag was not removed");
  }

}