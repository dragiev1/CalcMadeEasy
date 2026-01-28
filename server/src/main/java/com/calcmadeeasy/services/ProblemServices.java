package com.calcmadeeasy.services;

import java.util.List;
import java.util.UUID;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

import com.calcmadeeasy.dto.Problems.CreateProblemDTO;
import com.calcmadeeasy.dto.Problems.ProblemDTO;
import com.calcmadeeasy.dto.Problems.ProblemResponseDTO;
import com.calcmadeeasy.dto.Problems.UpdateProblemDTO;
import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Problems.ProblemSolutionType;
import com.calcmadeeasy.models.Tags.Tag;
import com.calcmadeeasy.repository.ProblemRepo;

import org.springframework.stereotype.Service;

@Service
public class ProblemServices {

  private final ProblemRepo repo;
  private final TagServices tagService;

  public ProblemServices(ProblemRepo repo, TagServices tagService) {
    this.repo = repo;
    this.tagService = tagService;
  }

  // ==================== CREATE ====================

  public ProblemResponseDTO createProblem(CreateProblemDTO problem) {
    Problem p = Problem.builder()
        .description(problem.getDescription())
        .isChallenge(problem.getIsChallenge())
        .points(problem.getPoints())
        .solution(problem.getSolution())
        .solutionType(problem.getSolutionType())
        .build();

    repo.save(p);

    return new ProblemResponseDTO(p);
  }

  public ProblemDTO addTag(UUID problemId, UUID tagId) {
    Problem p = getProblemEntity(problemId);
    Tag tag = tagService.getTagEntity(tagId);

    p.addTag(tag);
    repo.save(p);

    return new ProblemDTO(p);
  }

  // ==================== READ ====================

  public List<Problem> getAllProblem() {
    return repo.findAll();
  }

  public List<ProblemDTO> getAllProblemDTOs() {
    return repo.findAll()
        .stream()
        .map(ProblemDTO::new)
        .toList();
  }

  public Problem getProblemEntity(UUID id) {
    return repo.findById(id).orElse(null);
  }

  public ProblemDTO getProblemDTO(UUID pId) {
    ProblemDTO p = new ProblemDTO(getProblemEntity(pId));
    return p;
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

  public ProblemDTO updateProblem(UUID pId, UpdateProblemDTO request) {
    Problem p = getProblemEntity(pId);
    
    if(request.getDescription() != null) 
      p.setDescription(request.getDescription());
    if(request.getSolution() != null) 
      p.setSolution(request.getSolution());
    if(request.getSolutionType() != null)
      p.setSolutionType(request.getSolutionType());
    if(request.getIsChallenge() != null)
      p.setIsChallenge(request.getIsChallenge());
    if(request.getPoints() != null)
      p.setPoints(request.getPoints());

    repo.save(p);

    return new ProblemDTO(p);
  }

  // ==================== DELETE ====================

  public void removeProblem(UUID problemId) {
    if (!exists(problemId))
      throw new IllegalArgumentException("Problem to be removed does not exist");
    repo.deleteById(problemId);
  }

  public void removeTagFromProblem(UUID tagId, UUID problemId) {
    if (!exists(problemId))
      throw new IllegalArgumentException("Problem to be removed does not exist");
    Problem p = getProblemEntity(problemId);
    boolean removed = p.getTags().removeIf(t -> t.getId().equals(tagId));
    if (!removed)
      throw new IllegalArgumentException("Problem was found but tag was not removed");
  }

}