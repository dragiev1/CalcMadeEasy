package com.calcmadeeasy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calcmadeeasy.dto.Courses.CourseResponseDTO;
import com.calcmadeeasy.dto.Courses.CreateCourseDTO;
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

  // ---------------- CREATE ----------------

}
