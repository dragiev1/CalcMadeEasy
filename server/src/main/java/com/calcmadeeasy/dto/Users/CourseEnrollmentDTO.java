package com.calcmadeeasy.dto.Users;

import java.util.UUID;

import com.calcmadeeasy.dto.Courses.CourseResponseDTO;
import com.calcmadeeasy.models.Users.UserCourseEnrollment;

import lombok.Getter;

// Outbound only.
@Getter
public class CourseEnrollmentDTO {
  private UUID id;
  private CourseResponseDTO course;

  public CourseEnrollmentDTO(UserCourseEnrollment uce) {
    this.id = uce.getId();
    this.course = new CourseResponseDTO(uce.getCourse());
  }
}
