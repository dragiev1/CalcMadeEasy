package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.calcmadeeasy.dto.Chapters.ChapterDTO;
import com.calcmadeeasy.dto.Chapters.ChapterResponseDTO;
import com.calcmadeeasy.dto.Chapters.CreateChapterDTO;
import com.calcmadeeasy.dto.Chapters.UpdateChapterDTO;
import com.calcmadeeasy.dto.Sections.CreateSectionDTO;
import com.calcmadeeasy.dto.Sections.SectionResponseDTO;
import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.services.ChapterServices;
import com.calcmadeeasy.services.SectionServices;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ChapterServiceTest {
  @Autowired
  private ChapterServices chapterService;
  @Autowired
  private SectionServices sectionService;

  private Chapter chapter;
  private Section section;

  @BeforeEach
  public void setup() {

    CreateChapterDTO cdto = new CreateChapterDTO();
    cdto.setDescription("chapter description");
    cdto.setTitle("chapter title");
    ChapterResponseDTO chapterResponse = chapterService.createChapter(cdto);
    chapter = chapterService.getChapterEntity(chapterResponse.getId());

    CreateSectionDTO sdto = new CreateSectionDTO();
    sdto.setDescription("section description");
    sdto.setTitle("section title");
    SectionResponseDTO sectionResponse = sectionService.createSection(sdto);
    section = sectionService.getSectionEntity(sectionResponse.getId());

  }

  // Create

  @Test
  public void testCreateChapter() {
    boolean exists = chapterService.exists(chapter.getId());
    assertTrue(exists);
  }

  // Retrieve

  @Test
  public void testGetChapter() {
    Chapter c = chapterService.getChapterEntity(chapter.getId());
    assertEquals(c, chapter);
  }

  @Test
  public void testGetAllChapters() {
    int size = chapterService.getAllChapters().size();

    String err = "Error: number of chapters retrieved does not match expected";
    assertEquals(1, size, err);
  }

  // Update

  @Test
  public void testUpdateChapter() {
    UpdateChapterDTO update = new UpdateChapterDTO();
    String changed = "CHANGED";
    update.setDescription(changed);
    update.setTitle(changed);
    ChapterDTO dto = chapterService.updateChapter(chapter.getId(), update);
    String err = "Error: chapter was not updated correctly";
    assertEquals(changed, dto.getDescription(), err);
    assertEquals(changed, dto.getTitle(), err);
  }

  @Test
  public void testAddSection() {
    chapterService.addSection(chapter.getId(), section.getId());
    List<Section> sections = chapterService.getChapterEntity(chapter.getId()).getSections();
    boolean exists = sections.isEmpty();
    String err = "Error: section did not append to chapter properly";
    assertFalse(exists, err);
    assertEquals(1, sections.size(), err);
    assertEquals(section.getId(), sections.get(0).getId(), err);
  }

  @Test
  public void testRemoveChapter() {
    UUID ogId = chapter.getId();

    chapterService.removeChapter(ogId);
    boolean removed = chapterService.exists(ogId);

    String err = "Error: chapter persists after removal";
    assertFalse(removed, err);
  }

  @Test
  public void testRemoveSection() {
    chapterService.addSection(chapter.getId(), section.getId());
    assertEquals(1, chapter.getSections().size());
    UUID cId = chapter.getId();

    chapterService.removeSection(cId, section.getId());
    Chapter c = chapterService.getChapterEntity(cId);
    boolean exist = c.getSections().contains(section);
    int size = c.getSections().size();
    String err = "Error: section persists in chapter, was not removed";

    assertFalse(exist, err);
    assertEquals(0, size, err);
  }
}
