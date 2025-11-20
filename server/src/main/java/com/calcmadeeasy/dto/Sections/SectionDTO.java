package com.calcmadeeasy.dto.Sections;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Sections.Section;

// Outbound only.
public class SectionDTO {
  private UUID id;
  private String title;
  private String description;
  private List<Page> pages;
  private Instant createdAt;
  private Instant updatedAt;

  public SectionDTO() {
  }

  public SectionDTO(Section section) {
    this.id = section.getId();
    this.description = section.getDescription();
    this.title = section.getTitle();
    this.pages = section.getPages();
    this.createdAt = section.getCreatedAt();
    this.updatedAt = section.getUpdatedAt();
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

  public List<Page> getPages() {
    return pages;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  // Setters

  public void setId(UUID id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setPages(List<Page> pages) {
    this.pages = pages;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

}
