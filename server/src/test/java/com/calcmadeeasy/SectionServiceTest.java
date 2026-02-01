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
import com.calcmadeeasy.dto.Courses.CourseResponseDTO;
import com.calcmadeeasy.dto.Courses.CreateCourseDTO;
import com.calcmadeeasy.dto.Pages.CreatePageDTO;
import com.calcmadeeasy.dto.Pages.PageResponseDTO;
import com.calcmadeeasy.dto.Sections.CreateSectionDTO;
import com.calcmadeeasy.dto.Sections.SectionDTO;
import com.calcmadeeasy.dto.Sections.SectionResponseDTO;
import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.services.ChapterServices;
import com.calcmadeeasy.services.CourseServices;
import com.calcmadeeasy.services.PageServices;
import com.calcmadeeasy.services.SectionServices;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class SectionServiceTest {
  @Autowired
  private CourseServices courseService;
  @Autowired 
  private ChapterServices chapterService;
  @Autowired
  private SectionServices sectionService;
  @Autowired
  private PageServices pageService;

  private Page page;
  private Section section;
  private Chapter chapter;
  private Course course;

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
    ChapterResponseDTO chapterResponse = chapterService.createChapter(cdto);
    chapter = chapterService.getChapterEntity(chapterResponse.getId());

    CreateSectionDTO sdto = new CreateSectionDTO();
    sdto.setDescription("DESCRIPTION");
    sdto.setTitle("TITLE");
    sdto.setChapterId(chapter.getId());
    SectionResponseDTO response = sectionService.createSection(sdto);
    section = sectionService.getSectionEntity(response.getId());

    CreatePageDTO pdto = new CreatePageDTO();
    pdto.setContent("CONTENT");
    pdto.setPosition(0);
    PageResponseDTO pageResponse = pageService.createPage(pdto);
    page = pageService.getPageEntity(pageResponse.getId());

  }

  // CREATE

  @Test
  public void testCreateSection() {
    boolean exists = sectionService.exists(section.getId());
    String err = "Section was not saved";
    assertTrue(exists, err);
  }

  // READ

  @Test
  public void testGetAllPages() {
    int size = sectionService.getAllSections().size();
    boolean retrieved1 = sectionService.exists(section.getId());

    String err = "Error: section(s) was not saved properly ";
    assertEquals(1, size, err);
    assertTrue(retrieved1, err);
  }

  // UPDATE

  @Test
  public void testAddPage() {
    SectionDTO updated = sectionService.addPage(section.getId(), page.getId());
    int size = updated.getPages().size();

    String err = "Error: section was not saved properly";
    assertEquals(1, size, err);
    assertEquals(1, updated.getPages().get(0).getPosition());
    assertEquals(page.getId(), updated.getPages().get(0).getId(), err);
  }

  // DELETE

  @Test
  public void testRemoveSection() {
    UUID id = section.getId();

    sectionService.removeSection(id);
    boolean removed = sectionService.exists(id);

    String err = "Error: section persists upon deletion";
    assertFalse(removed, err);
  }

  @Test
  public void testRemovePage() {
    sectionService.addPage(section.getId(), page.getId());

    sectionService.removePage(section.getId(), page.getId());
    boolean exist = sectionService.getSectionEntity(section.getId()).getPages().isEmpty();

    String err = "Error: page was not removed from the section properly";
    assertTrue(exist, err);
  }
}
