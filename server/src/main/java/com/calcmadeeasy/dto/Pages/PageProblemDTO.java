package com.calcmadeeasy.dto.Pages;

import com.calcmadeeasy.models.Problems.ProblemType;

// Inbound only.
public class PageProblemDTO {
  private ProblemType problemType;

  public PageProblemDTO() {}

  // Getters

  public ProblemType getProblemType() {
    return problemType;
  }

}
