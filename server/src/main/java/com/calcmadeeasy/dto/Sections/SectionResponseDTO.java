package com.calcmadeeasy.dto.Sections;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Sections.Section;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

// Outbound only.
@Getter
public class SectionResponseDTO {
  private UUID id;
  @NotBlank(message = "Section title cannot be empty.")
  private String title;
  private String description;
  @NotNull(message = "Chapter ID for a given section cannot be null.")
  private UUID chapterId;
  private Instant updatedAt;
  private Instant createdAt;

  public SectionResponseDTO(Section section) {
    this.id = section.getId();
    this.description = section.getDescription();
    this.title = section.getTitle();
    this.chapterId = section.getChapter().getId();
    this.updatedAt = section.getUpdatedAt();
    this.createdAt = section.getCreatedAt();
  }
}
