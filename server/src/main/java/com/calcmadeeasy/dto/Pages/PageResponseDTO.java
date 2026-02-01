package com.calcmadeeasy.dto.Pages;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Pages.Page;

// Outbound only.
public class PageResponseDTO {
  private UUID id;
  private String content;
  private Integer position;
  private Instant createdAt;
  private Instant updatedAt;

  public PageResponseDTO() {}

  public PageResponseDTO(Page page) {
    this.id = page.getId();
    this.content = page.getContent();
    this.position = Integer.valueOf(page.getPosition());
    this.createdAt = page.getCreatedAt();
    this.updatedAt = page.getUpdatedAt();
  }

  public UUID getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public Integer getPosition() {
    return position;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  // No setters.

}
