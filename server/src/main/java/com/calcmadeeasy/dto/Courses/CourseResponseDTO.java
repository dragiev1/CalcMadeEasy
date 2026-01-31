package com.calcmadeeasy.dto.Courses;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Courses.Course;

// Outbound only.
public class CourseResponseDTO {
  private UUID id;
  private String description;
  private String title;
  private Instant updatedAt;
  private Instant createdAt;

  // No args constructor for Jackson.
  public CourseResponseDTO() {}

  public CourseResponseDTO(Course c) {
    this.id = c.getId();
    this.description = c.getDescription();
    this.title = c.getTitle();
    this.createdAt = c.getCreatedAt();
    this.updatedAt = c.getUpdatedAt();
  }

  // Getters

  public UUID getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  
}
