package com.calcmadeeasy.dto.Pages;

import com.calcmadeeasy.models.Problems.ProblemType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Inbound only.
@Getter
@NoArgsConstructor
public class PageProblemDTO {
  @NotNull(message = "Problem type must not be either exercise/homework.")
  private ProblemType problemType;
}
