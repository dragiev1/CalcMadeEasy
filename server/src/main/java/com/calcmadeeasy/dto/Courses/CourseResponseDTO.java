package com.calcmadeeasy.dto.Courses;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Courses.Course;

import lombok.Getter;

// Outbound only.
@Getter
public class CourseResponseDTO {
  private UUID id;
  private String description;
  private String title;
  private Instant updatedAt;
  private Instant createdAt;

  public CourseResponseDTO(Course c) {
    this.id = c.getId();
    this.description = c.getDescription();
    this.title = c.getTitle();
    this.createdAt = c.getCreatedAt();
    this.updatedAt = c.getUpdatedAt();
  }
}
