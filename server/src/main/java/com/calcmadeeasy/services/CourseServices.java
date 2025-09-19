package com.calcmadeeasy.services;

import com.calcmadeeasy.models.Courses.Course;

import org.springframework.stereotype.Service;


@Service
public class CourseServices {
  private final Course course;

  public CourseServices(Course course) {
    this.course = course;
  }

  // More complex methods here.

}
