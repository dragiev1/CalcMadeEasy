package com.calcmadeeasy.dto.Tags;


// Inbound only.
public class CreateTagDTO {
  private String tagName;
  private Double difficulty;

  // No args constructor for Jackson.
  public CreateTagDTO() {}

  // Getters

  public String getName() {
    return tagName;
  }

  public Double getDifficulty() {
    return difficulty;
  }

  // Setters

  public void setName(String tagName) {
    this.tagName = tagName;
  }

  public void setDifficulty(Double difficulty) {
    this.difficulty = difficulty;
  }

}
