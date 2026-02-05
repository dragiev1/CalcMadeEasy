package com.calcmadeeasy.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calcmadeeasy.dto.Courses.CourseDTO;
import com.calcmadeeasy.dto.Courses.CourseResponseDTO;
import com.calcmadeeasy.dto.Courses.CreateCourseDTO;
import com.calcmadeeasy.dto.Courses.UpdateCourseDTO;
import com.calcmadeeasy.services.CourseServices;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
  
  private CourseServices courseService;

  public CourseController(CourseServices courseService) {
    this.courseService = courseService;
  }

  // ---------------- CREATE ----------------

  @PostMapping
  public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CreateCourseDTO request) {
    CourseResponseDTO c = courseService.createCourse(request);
    return ResponseEntity.ok(c);
  }

  // ---------------- RETRIEVAL ----------------

  @GetMapping
  public ResponseEntity<List<CourseDTO>> getAllCourses() {
    List<CourseDTO> courses = courseService.getAllCoursesDTO();
    return ResponseEntity.ok(courses);
  }

  @GetMapping("/{courseId}")
  public ResponseEntity<CourseDTO> getCourse(@PathVariable UUID courseId) {
    CourseDTO c = courseService.getCourseDTO(courseId);
    return ResponseEntity.ok(c);
  }

  // ---------------- UPDATE ----------------

  @PutMapping("/{courseId}")
  public ResponseEntity<CourseResponseDTO> updateCourse(
    @PathVariable UUID courseId, 
    @RequestBody UpdateCourseDTO request) {
      CourseResponseDTO c = courseService.updateCourse(courseId, request);
      return ResponseEntity.ok(c);
  }

  @PutMapping("/move-chapter-to/{chapterId}/{courseId}")
  public ResponseEntity<CourseDTO> moveChapter(@PathVariable UUID courseId,
    @PathVariable UUID chapterId) {
      CourseDTO c = courseService.moveChapter(courseId, chapterId);
      return ResponseEntity.ok(c);
    }

  // ---------------- REMOVE ----------------

  @DeleteMapping("/{courseId}")
  public ResponseEntity<Void> removeCourse(@PathVariable UUID courseId) {
    courseService.removeCourse(courseId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{courseId}/remove-chapter/{chapterId}")
  public ResponseEntity<CourseDTO> removeChapter(@PathVariable UUID courseId, @PathVariable UUID chapterId) {
    CourseDTO c = courseService.removeChapter(courseId, chapterId);
    return ResponseEntity.ok(c);
  }

}
