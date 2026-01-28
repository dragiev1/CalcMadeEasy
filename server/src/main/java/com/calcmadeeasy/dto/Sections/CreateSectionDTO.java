package com.calcmadeeasy.dto.Sections;

import java.util.UUID;

// Inbound only.
public class CreateSectionDTO {
  private String title;
  private String description;
  private UUID chapterId;

  public CreateSectionDTO() {}

  // Getters

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public UUID getChapterId() {
    return chapterId;
  }


  // Setters

  public void setDescription(String description) {
    this.description = description;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setChapterId(UUID chapterId) {
    this.chapterId = chapterId;
  }

  
}
