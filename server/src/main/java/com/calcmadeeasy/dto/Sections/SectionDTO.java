package com.calcmadeeasy.dto.Sections;

import java.util.List;

import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Sections.Section;

// Outbound only.
public class SectionDTO extends SectionResponseDTO {
  private List<Page> pages;

  public SectionDTO() {}

  public SectionDTO(Section section) {
    super(section);
    this.pages = section.getPages();
  }

  // Getters.

  public List<Page> getPages() {
    return pages;
  }

}
