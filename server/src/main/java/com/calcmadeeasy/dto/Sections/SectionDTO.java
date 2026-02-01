package com.calcmadeeasy.dto.Sections;

import java.util.List;

import com.calcmadeeasy.dto.Pages.PageResponseDTO;
import com.calcmadeeasy.models.Sections.Section;

// Outbound only.
public class SectionDTO extends SectionResponseDTO {
  private List<PageResponseDTO> pages;

  public SectionDTO() {}

  public SectionDTO(Section section) {
    super(section);
    this.pages = section.getPages() == null ? List.of()
        : section.getPages().stream()
            .map(PageResponseDTO::new)
            .toList();
  }

  // Getters.

  public List<PageResponseDTO> getPages() {
    return pages;
  }

}
