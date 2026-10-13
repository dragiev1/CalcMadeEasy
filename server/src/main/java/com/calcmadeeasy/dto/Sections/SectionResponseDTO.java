package com.calcmadeeasy.dto.Sections;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Sections.Section;

import lombok.Getter;

// Outbound only.
@Getter
public class SectionResponseDTO {
  private UUID id;
  private String title;
  private String description;
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
