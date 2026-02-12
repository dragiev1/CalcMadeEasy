package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.calcmadeeasy.dto.Chapters.ChapterResponseDTO;
import com.calcmadeeasy.dto.Chapters.CreateChapterDTO;
import com.calcmadeeasy.dto.Chapters.UpdateChapterDTO;
import com.calcmadeeasy.dto.Courses.CourseResponseDTO;
import com.calcmadeeasy.dto.Courses.CreateCourseDTO;
import com.calcmadeeasy.dto.Sections.CreateSectionDTO;
import com.calcmadeeasy.dto.Sections.SectionResponseDTO;
import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.services.ChapterServices;
import com.calcmadeeasy.services.CourseServices;
import com.calcmadeeasy.services.SectionServices;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ChapterServiceTest {
  @Autowired
  private CourseServices courseService;
  @Autowired
  private ChapterServices chapterService;
  @Autowired
  private SectionServices sectionService;

  private Course course;
  private Chapter chapter;
  private Section section;

  @BeforeEach
  public void setup() {
    CreateCourseDTO codto = new CreateCourseDTO();
    codto.setDescription("course description");
    codto.setTitle("course title");
    CourseResponseDTO courseResponse = courseService.createCourse(codto);
    course = courseService.getCourseEntity(courseResponse.getId());

    CreateChapterDTO cdto = new CreateChapterDTO();
    cdto.setDescription("chapter description");
    cdto.setTitle("chapter title");
    cdto.setCourseId(course.getId());
    ChapterResponseDTO response = chapterService.createChapter(cdto);
    chapter = chapterService.getChapterEntity(response.getId());

    CreateSectionDTO sdto = new CreateSectionDTO();
    sdto.setDescription("section description");
    sdto.setTitle("section title");
    sdto.setChapterId(chapter.getId());
    SectionResponseDTO sectionResponse = sectionService.createSection(sdto);
    section = sectionService.getSectionEntity(sectionResponse.getId());

    assertTrue(chapter.getSectionQuantity() == 1);
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
    ChapterResponseDTO dto = chapterService.updateChapter(chapter.getId(), update);
    String err = "Error: chapter was not updated correctly";
    assertEquals(changed, dto.getDescription(), err);
    assertEquals(changed, dto.getTitle(), err);
  }

  @Test
  public void testMoveSection() {
    // Arrange the a new chapter to move section to.
    CreateChapterDTO testDTO = new CreateChapterDTO();
    testDTO.setCourseId(course.getId());
    testDTO.setDescription("test description");
    testDTO.setTitle("test title");
    ChapterResponseDTO testResponse = chapterService.createChapter(testDTO);
    Chapter testChapter = chapterService.getChapterEntity(testResponse.getId());

    chapterService.moveSection(testChapter.getId(), section.getId());
    int size = chapterService.getChapterEntity(chapter.getId()).getSections().size();

    String err = "Error: section move to chapter properly";
    assertEquals(0, size, err);
    assertEquals(1, testChapter.getSections().size(), err);
    assertEquals(section.getId(), testChapter.getSections().get(0).getId(), err);
  }

  // Remove

  @Test
  public void testRemoveChapter() {
    UUID id = chapter.getId();

    chapterService.removeChapter(id);

    String err = "Error: chapter persists after removal";
    assertTrue(chapterService.exists(id), err);
  }

  @Test
  public void testRemoveSection() {
    assertEquals(1, chapter.getSectionQuantity());
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
