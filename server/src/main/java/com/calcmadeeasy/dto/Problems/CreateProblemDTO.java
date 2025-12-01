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

  // Setters

  public void setDescription(String description) {
    this.description = description;
  }
 
  public void setPoints(Integer points) {
    this.points = points;
  }

  public void setIsChallenge(Boolean isChallenge) {
    this.isChallenge = isChallenge;
  }

  public void setSolution(String solution) {
    this.solution = solution;
  }

  public void setSolutionType(ProblemSolutionType solutionType) {
    this.solutionType = solutionType;
  }
  
}
