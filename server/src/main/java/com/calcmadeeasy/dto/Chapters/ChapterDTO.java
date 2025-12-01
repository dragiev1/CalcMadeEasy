package com.calcmadeeasy.dto.Chapters;

import java.util.List;

import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Sections.Section;

// Outbound only.
public class ChapterDTO extends ChapterResponseDTO {

  private List<Section> sections;

  public ChapterDTO() {
  }

  public ChapterDTO(Chapter chapter) {
    super(chapter);
    this.sections = chapter.getSections();

  }

  // Getters

  public List<Section> getSections() {
    return sections;
  }

}
