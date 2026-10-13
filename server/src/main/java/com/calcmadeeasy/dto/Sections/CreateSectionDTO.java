package com.calcmadeeasy.dto.Sections;

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
public class CreateSectionDTO {
  @NotBlank(message = "Section title cannot be empty.")
  private String title;
  private String description;
  @NotNull(message = "Chapter ID for a given section cannot be null.")
  private UUID chapterId;  
}
