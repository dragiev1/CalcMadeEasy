package com.calcmadeeasy.dto.Problems;

import com.calcmadeeasy.models.Problems.ProblemSolutionType;

// Inbound only.
public class CreateProblemDTO {

  private String description;
  private Integer points;
  private Boolean isChallenge;
  private String solution;
  private ProblemSolutionType solutionType;

  public CreateProblemDTO() {}

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
 
}
