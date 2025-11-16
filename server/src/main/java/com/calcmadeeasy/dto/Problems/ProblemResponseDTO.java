package com.calcmadeeasy.dto.Problems;

import java.util.UUID;

import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Problems.ProblemSolutionType;

// Outbound only.
public class ProblemResponseDTO {
  private UUID id;
  private String description;
  private int points;
  private boolean isChallenge;
  private String solution;
  private ProblemSolutionType solutionType;

  public ProblemResponseDTO() {
  }

  public ProblemResponseDTO(Problem problem) {
    this.id = problem.getId();
    this.description = problem.getDescription();
    this.isChallenge = problem.getIsChallenge();
    this.points = problem.getPoints();
    this.solution = problem.getSolution();
    this.solutionType = problem.getSolutionType();
  }

  // Getters

  public UUID getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public String getSolution() {
    return solution;
  }

  public ProblemSolutionType getSolutionType() {
    return solutionType;
  }

  public boolean getIsChallenge() {
    return isChallenge;
  }

  public int getPoints() {
    return points;
  }

  // No setters.
}
