package com.calcmadeeasy.dto.Problems;

import com.calcmadeeasy.models.Problems.ProblemSolutionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Inbound only.
@Getter
@Setter
@NoArgsConstructor
public class CreateProblemDTO {
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
}
