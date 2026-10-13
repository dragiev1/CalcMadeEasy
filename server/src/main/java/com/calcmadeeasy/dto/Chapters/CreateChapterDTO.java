package com.calcmadeeasy.dto.Chapters;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Inbound only.
@Getter
@Setter
@NoArgsConstructor
public class CreateChapterDTO {
  private String description;
  @NotBlank(message = "Chapter title cannot blank.")
  @NotNull(message = "Chapter title cannot be null.")
  private String title;
  private UUID courseId;
}
