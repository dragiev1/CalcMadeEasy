package com.calcmadeeasy.dto.Pages;

import java.util.UUID;

import com.calcmadeeasy.models.Problems.ProblemType;

// Inbound only.
public class PageProblemDTO {
  private UUID pageId;
  private UUID problemId;
  private ProblemType problemType;

  public PageProblemDTO() {
  }


  // Getters

  public UUID getPageId() {
    return pageId;
  }

  public UUID getProblemId() {
    return problemId;
  }

  public ProblemType getProblemType() {
    return problemType;
  }

  
  // No setters.
}
