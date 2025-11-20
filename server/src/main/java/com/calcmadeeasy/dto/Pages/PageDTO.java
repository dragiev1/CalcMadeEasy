package com.calcmadeeasy.dto.Pages;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problems.Problem;

// Outbound only.
public class PageDTO {
  private UUID id;
  private String content;
  private Integer problemQuantity;
  private List<Problem> exercises;
  private List<Problem> homework;
  private Instant createdAt;
  private Instant updatedAt;

  public PageDTO() {}

  public PageDTO(Page page) {
    this.id = page.getId();
    this.content = page.getContent();
    this.problemQuantity = page.getProblemQuantity();
    this.exercises = page.getExercises();
    this.homework = page.getHomework();
    this.createdAt = page.getCreatedAt();
    this.updatedAt = page.getUpdatedAt();
  }

  // Getters

    public UUID getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public List<Problem> getExercises() {
    return exercises;
  }

  public List<Problem> getHomework() {
    return homework;
  }

  public Integer getProblemQuantity() {
    return problemQuantity;
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

  public void setContent(String content) {
    this.content = content;
  }

  public void setExercises(List<Problem> exercises) {
    this.exercises = exercises;
  }

  public void setHomework(List<Problem> homework) {
    this.homework = homework;
  }

  public void setProblemQuantity(Integer problemQuantity) {
    this.problemQuantity = problemQuantity;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

}
