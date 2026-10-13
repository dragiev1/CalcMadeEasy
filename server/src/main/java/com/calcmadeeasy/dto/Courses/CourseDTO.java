package com.calcmadeeasy.dto.Courses;

import java.util.List;

import com.calcmadeeasy.dto.Chapters.ChapterResponseDTO;
import com.calcmadeeasy.models.Courses.Course;

import lombok.Getter;

// Outbound only.
@Getter
public class CourseDTO extends CourseResponseDTO {
  private List<ChapterResponseDTO> chapters;

  public CourseDTO(Course c) {
    super(c);
    this.chapters = c.getChapters() == null ? List.of()
        : c.getChapters().stream()
            .map(ChapterResponseDTO::new)
            .toList();
  }
}
