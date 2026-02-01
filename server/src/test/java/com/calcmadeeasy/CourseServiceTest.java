package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.calcmadeeasy.dto.Chapters.ChapterResponseDTO;
import com.calcmadeeasy.dto.Chapters.CreateChapterDTO;
import com.calcmadeeasy.dto.Courses.CourseResponseDTO;
import com.calcmadeeasy.dto.Courses.CreateCourseDTO;
import com.calcmadeeasy.dto.Courses.UpdateCourseDTO;
import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.services.ChapterServices;
import com.calcmadeeasy.services.CourseServices;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CourseServiceTest {

  @Autowired
  private CourseServices courseService;
  @Autowired
  private ChapterServices chapterService;

  private Course course;
  private Chapter chapter;

  @BeforeEach
  public void setup() {
    CreateCourseDTO dto = new CreateCourseDTO();
    dto.setDescription("course description");
    dto.setTitle("course title");
    CourseResponseDTO response = courseService.createCourse(dto);
    course = courseService.getCourseEntity(response.getId());

    CreateChapterDTO cdto = new CreateChapterDTO();
    cdto.setCourseId(course.getId());
    cdto.setDescription("chapter description");
    cdto.setTitle("chapter title");
    ChapterResponseDTO chapterResponse = chapterService.createChapter(cdto);
    chapter = chapterService.getChapterEntity(chapterResponse.getId());

  }

  // Create

  @Test
  public void testCreateCourse() {
    boolean exists = courseService.exists(course.getId());
    assertTrue(exists, "Error: could not save Course");
  }

  // Retrieval

  @Test
  public void testGetCourse() {
    UUID ogCourseId = course.getId();
    boolean exists = courseService.exists(course.getId());

    UUID cId = courseService.getCourseEntity(course.getId()).getId();
    assertEquals(true, exists, "Error: course was not created properly");
    assertEquals(ogCourseId, cId, "Error: ids do not match with retrieved and expected, get was unsuccessful");
    System.out.println("Successfully retrieved course");
  }

  // Update

  @Test
  public void testUpdateCourse() {
    String ogTitle = course.getTitle();
    String ogDesc = course.getDescription();
    String changed = "CHANGED";

    UpdateCourseDTO update = new UpdateCourseDTO();
    update.setDescription(changed);
    update.setTitle(changed);
    courseService.updateCourse(course.getId(), update);
    Course updated = courseService.getCourseEntity(course.getId());

    String err = "Error: description did not update correctly";
    assertEquals(changed, updated.getDescription(), err);
    assertEquals(changed, updated.getTitle(), err);
    assertNotEquals(ogDesc, updated.getDescription(), err);
    assertNotEquals(ogTitle, updated.getDescription(), err);
  }

  @Test
  public void testAddChapter() {
    List<Chapter> og = new ArrayList<>();
    og.add(chapter);

    courseService.addChapter(course.getId(), chapter.getId());

    Course updated = courseService.getCourseEntity(course.getId());
    boolean added = updated.getChapters().contains(chapter);

    String err = "Error: chapter was not appended to course correctly";
    assertTrue(added, err);
    assertNotEquals(og, updated.getChapters(), err);

    System.out.println("Successfully appended new chapter to course");
  }

  // Remove

  @Test
  public void testRemoveCourse() {
    UUID ogId = course.getId();

    courseService.removeCourse(ogId);
    boolean exist = courseService.exists(ogId);

    assertEquals(false, exist, "Error: course did not remove successfully");
    System.out.println("Successfully removed course");
  }

  @Test
  public void testRemoveChapter() {
    List<Chapter> og = new ArrayList<>();
    og.add(chapter);
    courseService.removeChapter(course.getId(), chapter.getId());
    boolean isEmpty = courseService.getCourseEntity(course.getId()).getChapters().isEmpty();

    String err = "Error: chapter was not removed from course properly";
    assertEquals(true, isEmpty, err);
    assertNotEquals(og, courseService.getCourseEntity(course.getId()).getChapters(), err);
    System.out.println("Successfully removed chapter from course");
  }
}
