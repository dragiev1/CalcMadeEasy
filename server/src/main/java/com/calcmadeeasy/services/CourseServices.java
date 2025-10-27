package com.calcmadeeasy.services;

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

  public Course createCourse(Course course) {
    return repo.save(course);
  }

  public List<Course> createCourses(Course... courses) {
    return repo.saveAll(List.of(courses));
  }

  public List<Course> createCourses(List<Course> courses) {
    return repo.saveAll(courses);
  }

  // ==================== RETRIEVAL ====================

  public Course getCourse(UUID courseId) {
    return repo.findById(courseId)
        .orElseThrow(() -> new IllegalArgumentException("No course found with id: " + courseId));
  }

  public List<Course> getCourses() {
    return repo.findAll();
  }

  public boolean exists(UUID courseId) {
    return repo.existsById(courseId);
  }

  // ==================== UPDATE ====================

  public void updateTitle(UUID courseId, String newTitle) {
    Course c = getCourse(courseId);
    c.setTitle(newTitle);
    repo.save(c);
  }

  public void updateDescription(UUID courseId, String newDescription) {
    Course c = getCourse(courseId);
    c.setDescription(newDescription);
    repo.save(c);
  }

  public Course addChapter(UUID courseId, Chapter chapter) {
    Course c = getCourse(courseId);
    c.setChapters(chapter);
    return repo.save(c);
  }

  // ==================== REMOVE ====================

  public void removeChapter(UUID courseId) {
    boolean exists = exists(courseId);
    if (!exists)
      throw new IllegalArgumentException("Course does not exist with id: " + courseId);

    repo.deleteById(courseId);
  }

  public void removeChapter(UUID courseId, UUID chapterId) {
    Course c = getCourse(courseId);
    boolean removed = c.getChapters().removeIf(x -> x.getId().equals(chapterId));  
    if(!removed) throw new IllegalArgumentException("Chapter does not exist in course, could not delete");
    repo.save(c);  
  }
}
