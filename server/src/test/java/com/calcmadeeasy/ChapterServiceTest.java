package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;
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
  private Chapter chapter2;
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
    chapter2 = Chapter.builder()
        .description("description2")
        .title("title2")
        .sections(section1, section2)
        .build();
    chapterService.createChapter(chapter);
  }

  // Create

  @Test
  public void testCreateChapter() {
    chapterService.createChapter(chapter);
    boolean exists = chapterService.exists(chapter.getId());
    assertTrue(exists);
  }

  // Retrieve

  @Test
  public void testGetChapter() {
    Chapter c = chapterService.getChapter(chapter.getId());
    assertEquals(c, chapter);
    System.out.println("Successfully retrieved chapter");
  }

  @Test
  public void testGetAllChapters() {
    chapterService.createChapter(chapter2);
    int size = chapterService.getAllChapters().size();

    String err = "Error: number of chapters retrieved does not match expected";
    assertEquals(2, size, err);
    System.out.println("Successfully retrieved all chapters");
  }

  // Update

  @Test
  public void testUpdateDescription() {
    String changed = "CHANGED";
    chapterService.updateDescription(chapter.getId(), changed);
    String newDesc = chapterService.getChapter(chapter.getId()).getDescription();

    assertNotEquals("description", newDesc);
    assertEquals(changed, newDesc);
    System.out.println("Successfully updated the description");
  }

  @Test
  public void testUpdateTitle() {
    String changed = "CHANGED";
    chapterService.updateTitle(chapter.getId(), changed);
    String newTitle = chapterService.getChapter(chapter.getId()).getTitle();

    assertEquals(changed, newTitle);
    assertNotEquals("title", newTitle);
    System.out.println("Successfully updated title");
  }

  @Test
  public void testAddSection() {
    List<Section> og = new ArrayList<>();
    og.add(section1);

    chapterService.addSection(chapter.getId(), section2);
    Chapter updated = chapterService.getChapter(chapter.getId());
    boolean added = updated.getSections().contains(section2);

    String err = "Error: section did not append to chapter properly";
    assertTrue(added, err);
    assertNotEquals(og, updated.getSections(), err);
    System.out.println("Successfully added new section");
  }

  @Test
  public void testRemoveChapter() {
    UUID ogId = chapter.getId();
    chapterService.removeChapter(ogId);
    boolean removed = chapterService.exists(ogId);

    String err = "Error: chapter persists after removal";
    assertFalse(removed, err);
    System.out.println("Successfully removed chapter");
  }

  @Test
  public void testRemoveSection() {
    UUID cId = chapter.getId();
    chapterService.removeSection(cId, section1.getId());
    Chapter c = chapterService.getChapter(cId);
    boolean exist = c.getSections().contains(section1);
    int size = c.getSections().size();

    assertFalse(exist, "Error: section persists in chapter, was not deleted");
    assertEquals(0, size);
    System.out.println("Successfully removed section");
  }
}
