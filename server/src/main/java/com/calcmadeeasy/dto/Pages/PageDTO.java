package com.calcmadeeasy.dto.Pages;

import java.util.List;

import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problems.Problem;

// Outbound only.
public class PageDTO extends PageResponseDTO {
  private Integer position;
  private List<Problem> exercises;
  private List<Problem> homework;

  public PageDTO() {}

  public PageDTO(Page page) {
    super(page);
    this.position = page.getPosition();
    this.exercises = page.getExercises();
    this.homework = page.getHomework();
  }

  // Getters

  public List<Problem> getExercises() {
    return exercises;
  }

  public List<Problem> getHomework() {
    return homework;
  }

  public Integer getPosition() {
    return position;
  }

  // No setters

}
