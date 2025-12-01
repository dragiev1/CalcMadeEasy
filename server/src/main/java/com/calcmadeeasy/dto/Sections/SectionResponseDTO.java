package com.calcmadeeasy.dto.Sections;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Sections.Section;

// Outbound only.
public class SectionResponseDTO {
  private UUID id;
  private String title;
  private String description;
  private UUID chapterId;
  private Instant updatedAt;
  private Instant createdAt;

  public SectionResponseDTO() {}

  public SectionResponseDTO(Section section) {
    this.id = section.getId();
    this.description = section.getDescription();
    this.title = section.getTitle();
    this.chapterId = section.getChapterId();
    this.updatedAt = section.getUpdatedAt();
    this.createdAt = section.getCreatedAt();
  }

  // Getters.

  public UUID getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public UUID getChapterId() {
    return chapterId;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
  
}
