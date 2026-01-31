package com.calcmadeeasy.dto.Chapters;

import java.util.UUID;

// Inbound only.
public class CreateChapterDTO {
  private String description;
  private String title;
  private UUID courseId;

  public CreateChapterDTO() {}

  // Getters

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public UUID getCourseId() {
    return courseId;
  }

  // Setters

  public void setDescription(String description) {
    this.description = description;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setCourseId(UUID courseId) {
    this.courseId = courseId;
  }

}
