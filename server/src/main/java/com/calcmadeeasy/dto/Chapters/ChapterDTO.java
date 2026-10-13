package com.calcmadeeasy.dto.Chapters;

import java.util.List;

import com.calcmadeeasy.dto.Sections.SectionResponseDTO;
import com.calcmadeeasy.models.Chapters.Chapter;

import lombok.Getter;

// Outbound only.
@Getter
public class ChapterDTO extends ChapterResponseDTO {

  // Contains list of section response dtos instead of entities.
  private List<SectionResponseDTO> sections;

  public ChapterDTO(Chapter chapter) {
    super(chapter);
    this.sections = chapter.getSections() == null ? List.of()
        : chapter.getSections().stream()
            .map(SectionResponseDTO::new)
            .toList();
  }
}
