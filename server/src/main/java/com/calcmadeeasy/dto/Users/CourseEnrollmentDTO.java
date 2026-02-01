package com.calcmadeeasy.dto.Users;

import java.util.UUID;

import com.calcmadeeasy.dto.Courses.CourseResponseDTO;
import com.calcmadeeasy.models.Users.UserCourseEnrollment;

// Outbound only.
public class CourseEnrollmentDTO {
  
  private UUID id;
  private UserResponseDTO user;
  private CourseResponseDTO course;

  public CourseEnrollmentDTO() {}

  public CourseEnrollmentDTO(UserCourseEnrollment uce) {
    this.id = uce.getId();
    this.user = new UserResponseDTO(uce.getUser());
    this.course = new CourseResponseDTO(uce.getCourse());
  }

  // Getters

  public UUID getId() {
    return id;
  }

  public UserResponseDTO getUser() {
    return user;
  }

  public CourseResponseDTO getCourse() {
    return course;
  }

  // No setters; immutable.
}
