package com.calcmadeeasy.dto.Pages;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Pages.Page;

// Outbound only.
public class PageResponseDTO {
  private UUID id;
  private String content;
  private Integer problemQuantity;
  private Instant createdAt;
  private Instant updatedAt;

  public PageResponseDTO() {}

  public PageResponseDTO(Page page) {
    this.id = page.getId();
    this.content = page.getContent();
    this.problemQuantity = page.getProblemQuantity();
    this.createdAt = page.getCreatedAt();
    this.updatedAt = page.getUpdatedAt();
  }

  public UUID getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public int getProblemQuantity() {
    return problemQuantity;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  // Setters

  public void setId(UUID id) {
    this.id = id;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setProblemQuantity(Integer problemQuantity) {
    this.problemQuantity = problemQuantity;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }
}
