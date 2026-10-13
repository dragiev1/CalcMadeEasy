package com.calcmadeeasy.dto.Problems;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Problems.ProblemSolutionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

// Outbound only.
@Getter
public class ProblemResponseDTO {
  private UUID id;
  @NotBlank(message = "Description cannot be blank.")
  private String description;
  @PositiveOrZero(message = "Points must be a value >= 0.")
  @NotNull(message = "Points cannot be null.")
  private Integer points;
  @NotNull(message = "Challenge boolean cannot be null.")
  private Boolean isChallenge;
  @NotNull(message = "Solution cannot be null.")
  @NotBlank(message = "Solution cannot be empty.")
  private String solution;
  @NotNull(message = "Solution type must be an enumeration of either NUMERICAL or EXPRESSION.")
  private ProblemSolutionType solutionType;
  private Instant createdAt;

  public ProblemResponseDTO(Problem problem) {
    this.id = problem.getId();
    this.description = problem.getDescription();
    this.isChallenge = problem.getIsChallenge();
    this.points = problem.getPoints();
    this.solution = problem.getSolution();
    this.solutionType = problem.getSolutionType();
    this.createdAt = problem.getCreatedAt();
  }
}
