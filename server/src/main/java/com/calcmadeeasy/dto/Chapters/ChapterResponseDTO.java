package com.calcmadeeasy.dto.Chapters;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Chapters.Chapter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

// Outbound
@Getter
public class ChapterResponseDTO {
  private UUID id;
  private String description;
  @NotBlank(message = "Chapter title cannot blank.")
  @NotNull(message = "Chapter title cannot be null.")
  private String title;
  private Instant createdAt;
  private Instant updatedAt;

  public ChapterResponseDTO(Chapter chapter) {
    this.id = chapter.getId();
    this.description = chapter.getDescription();
    this.title = chapter.getTitle();
    this.createdAt = chapter.getCreatedAt();
    this.updatedAt = chapter.getUpdatedAt();
  }
}
