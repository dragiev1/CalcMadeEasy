package com.calcmadeeasy.dto.Tags;

import com.calcmadeeasy.models.Tags.Tag;

// Inbound only.
public class CreateTagDTO {
  private String name;
  private Double difficulty;

  // No args constructor for Jackson.
  public CreateTagDTO() {
  }

  public CreateTagDTO(Tag t) {
    this.name = t.getTagName();
    this.difficulty = t.getDifficulty();
  }

  // Getters

  public String getName() {
    return name;
  }

  public Double getDifficulty() {
    return difficulty;
  }

  // Setters

  public void setName(String name) {
    this.name = name;
  }

  public void setDifficulty(Double difficulty) {
    this.difficulty = difficulty;
  }

}
