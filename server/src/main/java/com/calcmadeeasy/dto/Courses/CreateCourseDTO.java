package com.calcmadeeasy.dto.Courses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Inbound only.
@Getter
@Setter
@NoArgsConstructor
public class CreateCourseDTO {
  private String description;
  @NotBlank(message = "Course title cannot be blank.")
  @NotNull(message = "Course title cannot be null.")
  private String title;
}
