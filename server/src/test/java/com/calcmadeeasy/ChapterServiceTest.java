package com.calcmadeeasy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.services.ChapterServices;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ChapterServiceTest {
  @Autowired
  private ChapterServices chapterService;

  private Chapter chapter;
  private Section section1;
  private Section section2;

  @BeforeEach
  public void setup() {
    section1 = Section.builder()
      .description("description1")
      .title("title1")
      .build();
    section2 = Section.builder()
      .description("description2")
      .title("title2")
      .build();
    chapter = Chapter.builder()
      .description("description")
      .title("title")
      .sections(section1)
      .build();
    chapterService.createChapter(chapter);
  }

  @Test
  public void testCreateChapter() {

  }
}
