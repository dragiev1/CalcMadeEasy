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

import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.services.CourseServices;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CourseServiceTest {

  @Autowired
  private CourseServices courseService;

  private Course course;
  private Chapter chapter1;
  private Chapter chapter2;

  @BeforeEach
  public void setup() {
    
  }

  // Create

  @Test
  public void testCreateCourse() {
    courseService.createCourse(course);
    boolean exists = courseService.exists(course.getId());
    assertTrue(exists, "Error: could not save Course");
  }

  // Retrieval

  @Test
  public void testGetCourse() {
    courseService.createCourse(course);
    UUID ogCourseId = course.getId();
    boolean exists = courseService.exists(course.getId());

    UUID cId = courseService.getCourse(course.getId()).getId();
    assertEquals(true, exists, "Error: course was not created properly");
    assertEquals(ogCourseId, cId, "Error: ids do not match with retrieved and expected, get was unsuccessful");
    System.out.println("Successfully retrieved course");
  }

  // Update

  @Test
  public void testUpdateTitle() {
    String ogTitle = course.getTitle();
    String changed = "CHANGED";
    courseService.createCourse(course);
    courseService.updateTitle(course.getId(), changed);

    String err = "Error: title did not update correctly";
    assertNotEquals(ogTitle, courseService.getCourse(course.getId()).getTitle(), err);
    assertEquals(changed, courseService.getCourse(course.getId()).getTitle(), err);
    System.out.println("Successfully updated title");
  }

  @Test
  public void testUpdateDescription() {
    String ogDesc = course.getDescription();
    String changed = "CHANGED";
    courseService.createCourse(course);
    courseService.updateDescription(course.getId(), changed);

    String err = "Error: description did not update correctly";
    assertEquals(changed, courseService.getCourse(course.getId()).getDescription(), err);
    assertNotEquals(ogDesc, courseService.getCourse(course.getId()).getDescription(), err);
  }

  @Test
  public void testAddChapter() {
    List<Chapter> og = new ArrayList<>();
    og.add(chapter1);

    Course savedCourse = courseService.createCourse(course);
    courseService.addChapter(savedCourse.getId(), chapter2);

    Course updated = courseService.getCourse(savedCourse.getId());
    boolean added = updated.getChapters().contains(chapter2);

    String err = "Error: chapter was not appended to course correctly";
    assertTrue(added, err);
    assertNotEquals(og, updated.getChapters(), err);

    System.out.println("Successfully appended new chapter to course");
  }

  // Remove

  @Test
  public void testRemoveCourse() {
    courseService.createCourse(course);
    UUID ogId = course.getId();

    courseService.removeCourse(ogId);
    boolean exist = courseService.exists(ogId);

    assertEquals(false, exist, "Error: course did not remove successfully");
    System.out.println("Successfully removed course");
  }

  @Test
  public void testRemoveChapter() {
    List<Chapter> og = new ArrayList<>();
    og.add(chapter1);
    courseService.createCourse(course);
    courseService.removeChapter(course.getId(), chapter1.getId());
    boolean isEmpty = courseService.getCourse(course.getId()).getChapters().isEmpty();

    String err = "Error: chapter was not removed from course properly";
    assertEquals(true, isEmpty, err);
    assertNotEquals(og, courseService.getCourse(course.getId()).getChapters(), err);
    System.out.println("Successfully removed chapter from course");
  }
}
