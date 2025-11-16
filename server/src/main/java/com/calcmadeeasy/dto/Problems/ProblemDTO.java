package com.calcmadeeasy.dto.Problems;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Problems.ProblemSolutionType;
import com.calcmadeeasy.models.Tags.Tag;

// Outbound only.
public class ProblemDTO {
  private UUID id;
  private String description;
  private int points;
  private boolean isChallenge;
  private String solution;
  private ProblemSolutionType solutionType;
  private Set<Tag> tags;
  private Instant createdAt;
  private Instant updatedAt;

  public ProblemDTO(Problem problem) {
    this.id = problem.getId();
    this.description = problem.getDescription();
    this.isChallenge = problem.getIsChallenge();
    this.points = problem.getPoints();
    this.solution = problem.getSolution();
    this.solutionType = problem.getSolutionType();
    this.tags = problem.getTags();
    this.createdAt = problem.getCreatedAt();
    this.updatedAt = problem.getUpdatedAt();
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

  public Set<Tag> getTags() {
    return tags;
  }

  public int getPoints() {
    return points;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  // Setters

  public void setId(UUID id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public void setSolution(String solution) {
    this.solution = solution;
  }

  public void setSolutionType(ProblemSolutionType solutionType) {
    this.solutionType = solutionType;
  }

  public void setIsChallenge(boolean isChallenge) {
    this.isChallenge = isChallenge;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }
}
