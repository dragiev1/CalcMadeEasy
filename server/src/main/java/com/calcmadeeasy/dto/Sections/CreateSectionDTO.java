package com.calcmadeeasy.dto.Sections;


// Inbound only.
public class CreateSectionDTO {
  private String title;
  private String description;

  public CreateSectionDTO() {}

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
