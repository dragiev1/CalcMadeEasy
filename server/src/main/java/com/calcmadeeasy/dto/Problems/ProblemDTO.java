package com.calcmadeeasy.dto.Problems;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.calcmadeeasy.dto.Tags.TagDTO;
import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Problems.ProblemSolutionType;

// Outbound only.
public class ProblemDTO {
  private UUID id;
  private String description;
  private Integer points;
  private Boolean isChallenge;
  private String solution;
  private ProblemSolutionType solutionType;
  private Set<TagDTO> tags;
  private Instant createdAt;
  private Instant updatedAt;

  public ProblemDTO(Problem problem) {
    this.id = problem.getId();
    this.description = problem.getDescription();
    this.isChallenge = problem.getIsChallenge();
    this.points = problem.getPoints();
    this.solution = problem.getSolution();
    this.solutionType = problem.getSolutionType();
    this.tags = problem.getTags().stream().map(TagDTO::new).collect(Collectors.toSet());
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

  public Boolean getIsChallenge() {
    return isChallenge;
  }

  public Set<TagDTO> getTags() {
    return tags;
  }

  public Integer getPoints() {
    return points;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public TagDTO getTagById(UUID tagId) {
    return tags.stream()
        .filter(tag -> tag.getId().equals(tagId))
        .findFirst()
        .orElse(null);
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

  public void setIsChallenge(Boolean isChallenge) {
    this.isChallenge = isChallenge;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }
}
