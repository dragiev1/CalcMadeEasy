package com.calcmadeeasy.dto.Courses;

import java.time.Instant;
// import java.util.List;
import java.util.UUID;

// import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;

// Outbound only.
// TODO: Finish controllers in order of lowest hierarchy to highest.
public class CourseDTO {
  private UUID id;
  private String description;
  private String title;
  // private List<ChapterDTO> chapters;
  private Instant updatedAt;
  private Instant createdAt;

  // No args contructor for Jackson.
  public CourseDTO() {}

  public CourseDTO(Course c) {
    this.id = c.getId();
    this.description = c.getDescription();
    this.title = c.getTitle();
    // this.chapters = chapters.stream().map(CourseDTO::new).toList();
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

  // public List<ChapterDTO> getChapters() {
  //   return chapters;
  // }

  // public int getChapterQuantity() {
  //   return chapters.size();
  // }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

}
