package com.calcmadeeasy.dto.Courses;

import java.util.List;

import com.calcmadeeasy.dto.Chapters.ChapterResponseDTO;
import com.calcmadeeasy.models.Courses.Course;

// Outbound only.
public class CourseDTO extends CourseResponseDTO {
  private List<ChapterResponseDTO> chapters;

  // No args contructor for Jackson.
  public CourseDTO() {}

  public CourseDTO(Course c) {
    super(c);
    this.chapters = c.getChapters() == null ? List.of()
        : c.getChapters().stream()
            .map(ChapterResponseDTO::new)
            .toList();
  }

  // Getters

  public List<ChapterResponseDTO> getChapters() {
    return chapters;
  }

  public int getChapterQuantity() {
    return chapters.size();
  }

}
