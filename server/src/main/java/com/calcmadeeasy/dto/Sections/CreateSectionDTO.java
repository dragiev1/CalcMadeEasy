package com.calcmadeeasy.dto.Sections;

import com.calcmadeeasy.models.Sections.Section;

// Inbound only.
public class CreateSectionDTO {
  private String title;
  private String description;

  public CreateSectionDTO() {}

  public CreateSectionDTO(Section section) {
    this.title = section.getTitle();
    this.description = section.getDescription();
  }

  // Getters

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }


  // No setters.
}
