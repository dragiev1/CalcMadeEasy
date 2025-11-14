package com.calcmadeeasy.dto.Tags;

import com.calcmadeeasy.models.Tags.Tag;

// Inbound only.
public class CreateTagDTO {
  private String name;
  private double difficulty;

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

  public double getDifficulty() {
    return difficulty;
  }

  // Setters

  public void setName(String name) {
    this.name = name;
  }

  public void setDifficulty(double difficulty) {
    this.difficulty = difficulty;
  }

}
