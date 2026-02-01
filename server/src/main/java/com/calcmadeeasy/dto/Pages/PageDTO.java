package com.calcmadeeasy.dto.Pages;

import java.util.List;

import com.calcmadeeasy.dto.Problems.ProblemResponseDTO;
import com.calcmadeeasy.models.Pages.Page;

// Outbound only.
public class PageDTO extends PageResponseDTO {
  private List<ProblemResponseDTO> exercises;
  private List<ProblemResponseDTO> homework;

  public PageDTO() {}

  public PageDTO(Page page) {
    super(page);
    this.exercises = page.getExercises() == null ? List.of()
        : page.getExercises().stream().map(ProblemResponseDTO::new).toList();
    this.homework = page.getHomework() == null ? List.of()
        : page.getHomework().stream().map(ProblemResponseDTO::new).toList();
  }

  // Getters

  public List<ProblemResponseDTO> getExercises() {
    return exercises;
  }

  public List<ProblemResponseDTO> getHomework() {
    return homework;
  }

  // No setters

}
