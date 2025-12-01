package com.calcmadeeasy.dto.Chapters;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Chapters.Chapter;

public class ChapterResponseDTO {
  private UUID id;
  private String description;
  private String title;
  private Instant createdAt;
  private Instant updatedAt;

  public ChapterResponseDTO() {}

  public ChapterResponseDTO(Chapter chapter) {
    this.id = chapter.getId();
    this.description = chapter.getDescription();
    this.title = chapter.getTitle();
    this.createdAt = chapter.getCreatedAt();
    this.updatedAt = chapter.getUpdatedAt();
  }

  // Getters

  public UUID getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }
}
