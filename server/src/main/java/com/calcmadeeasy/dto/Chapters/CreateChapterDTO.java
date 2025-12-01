package com.calcmadeeasy.dto.Chapters;


// Inbound only.
public class CreateChapterDTO {
  private String description;
  private String title;

  public CreateChapterDTO() {}

  // Getters

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  // Setters

  public void setDescription(String description) {
    this.description = description;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
