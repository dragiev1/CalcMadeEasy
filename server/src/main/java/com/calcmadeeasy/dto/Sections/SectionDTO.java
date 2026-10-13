package com.calcmadeeasy.dto.Sections;

import java.util.List;

import com.calcmadeeasy.dto.Pages.PageResponseDTO;
import com.calcmadeeasy.models.Sections.Section;

import lombok.Getter;

// Outbound only.
@Getter
public class SectionDTO extends SectionResponseDTO {
  private List<PageResponseDTO> pages;

  public SectionDTO(Section section) {
    super(section);
    this.pages = section.getPages() == null ? List.of()
        : section.getPages().stream()
            .map(PageResponseDTO::new)
            .toList();
  }
}
