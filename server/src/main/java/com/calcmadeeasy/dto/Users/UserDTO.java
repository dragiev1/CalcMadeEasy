package com.calcmadeeasy.dto.Users;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import com.calcmadeeasy.models.Users.User;

import lombok.Getter;

/*
 * Data Transfer Object for all/single User requests. 
 * Outbound only. 
 */
@Getter
public class UserDTO extends UserResponseDTO {
  private Set<CourseEnrollmentDTO> coursesEnrolled;
  private Instant updatedAt;

  public UserDTO(User user) {
    super(user);
    this.coursesEnrolled = user.getCoursesEnrolled() == null ? Collections.emptySet()
        : user.getCoursesEnrolled().stream()
            .map(CourseEnrollmentDTO::new)
            .collect(Collectors.toSet());
    this.updatedAt = user.getUpdatedAt();
  }
}
