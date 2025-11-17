package com.calcmadeeasy.dto.Problems;

import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Problems.ProblemSolutionType;

// Inbound only.
public class CreateProblemDTO {

  private String description;
  private Integer points;
  private Boolean isChallenge;
  private String solution;
  private ProblemSolutionType solutionType;

  public CreateProblemDTO() {
  }

  public CreateProblemDTO(Problem p) {
    this.description = p.getDescription();
    this.points = p.getPoints();
    this.isChallenge = p.getIsChallenge();
    this.solution = p.getSolution();
    this.solutionType = p.getSolutionType();
  }

  // Getters

  public String getDescription() {
    return description;
  }

  public String getSolution() {
    return solution;
  }

  public ProblemSolutionType getSolutionType() {
    return solutionType;
  }

  public Boolean getIsChallenge() {
    return isChallenge;
  }

  public Integer getPoints() {
    return points;
  }

  // No setters
}
