package com.calcmadeeasy.services;

import com.calcmadeeasy.dto.Courses.CourseDTO;
import com.calcmadeeasy.dto.Courses.CourseResponseDTO;
import com.calcmadeeasy.dto.Courses.CreateCourseDTO;
import com.calcmadeeasy.dto.Courses.UpdateCourseDTO;
import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.repository.CourseRepo;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class CourseServices {
  private final CourseRepo repo;

  public CourseServices(CourseRepo repo) {
    this.repo = repo;
  }

  // ==================== CREATE ====================

  public CourseResponseDTO createCourse(CreateCourseDTO course) {
    Course c = Course.builder()
        .description(course.getDescription())
        .title(course.getTitle())
        .build();
    repo.save(c);
    return new CourseResponseDTO(c);
  }

  // ==================== READ ====================

  public Course getCourseEntity(UUID courseId) {
    return repo.findById(courseId)
        .orElseThrow(() -> new IllegalArgumentException("No course found with id: " + courseId));
  }

  public CourseDTO getCourseDTO(UUID courseId) {
    Course c = repo.findById(courseId)
        .orElseThrow(() -> new IllegalArgumentException("No course found with id: " + courseId));

    return new CourseDTO(c);
  }

  public List<CourseDTO> getAllCoursesDTO() {
    return repo.findAll().stream().map(CourseDTO::new).toList();
  }

  public boolean exists(UUID courseId) {
    return repo.existsById(courseId);
  }

  // ==================== UPDATE ====================

  public CourseResponseDTO updateCourse(UUID courseId, UpdateCourseDTO request) {
    if (request == null)
      throw new IllegalArgumentException("Data sent is null; did not update course.");

    Course c = getCourseEntity(courseId);

    if (request.getDescription() != null)
      c.setDescription(request.getDescription());
    if (request.getTitle() != null)
      c.setDescription(request.getTitle());

    repo.save(c);
    return new CourseResponseDTO(c);
  }

  public CourseDTO addChapter(UUID courseId, Chapter chapter) {
    if (chapter == null)
      throw new IllegalArgumentException("Cannot append null chapter");

    Course c = getCourseEntity(courseId);

    chapter.setCourse(c);
    c.addChapter(chapter);

    repo.save(c);
    return new CourseDTO(c);
  }

  // ==================== DELETE ====================

  public void removeCourse(UUID courseId) {
    boolean exists = exists(courseId);
    if (!exists)
      throw new IllegalArgumentException("Course does not exist with id: " + courseId);

    repo.deleteById(courseId);
  }

  public void removeChapter(UUID courseId, UUID chapterId) {
    Course c = getCourseEntity(courseId);
    boolean removed = c.getChapters().removeIf(ch -> ch.getId().equals(chapterId));
    if (!removed)
      throw new IllegalArgumentException("Chapter does not exist in course, could not remove");
    repo.save(c);
  }
}
