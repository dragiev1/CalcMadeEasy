package com.calcmadeeasy.dto.Courses;


// Inbound only.
public class CreateCourseDTO {
  private String description;
  private String title;

  // No args constructor for Jackson.
  public CreateCourseDTO () {}

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
