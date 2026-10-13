package com.calcmadeeasy.dto.Pages;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Pages.Page;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

// Outbound only.
@Getter
public class PageResponseDTO {
  @NotNull(message = "Page ID must not be null")
  @NotBlank(message = "Page ID must not be empty")
  private UUID id;
  private String content;
  @NotBlank(message = "Page position must not be empty")
  @Positive(message = "Page position must be a positive integer.")
  private Integer position;
  private Instant createdAt;
  private Instant updatedAt;

  public PageResponseDTO(Page page) {
    this.id = page.getId();
    this.content = page.getContent();
    this.position = Integer.valueOf(page.getPosition());
    this.createdAt = page.getCreatedAt();
    this.updatedAt = page.getUpdatedAt();
  }
}
