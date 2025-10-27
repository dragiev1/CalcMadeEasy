package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.services.CourseServices;

import jakarta.transaction.Transactional;

@SpringBootApplication
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
    chapter1 = Chapter.builder().description("description1").title("title1").build();
    chapter2 = Chapter.builder().description("description2").title("title2").build();
    course = Course.builder().description("description").title("title").chapters(chapter1).build();
  }

  // Create

  @Test
  public void testCreateCourse() {
    courseService.createCourse(course);
    boolean exists = courseService.exists(course.getId());
    assertEquals(true, exists, "Error: could not save Course");
  }

  @Test
  public void testAddCourse() {
    courseService.createCourse(course);
    courseService.addChapter(course.getId(), chapter2);
    List<Chapter> chapters = courseService.getCourse(course.getId()).getChapters();
    String err = "Error: chapter did not append to the course correctly";
    assertEquals(true, chapters.contains(chapter2), err);
    System.out.println("Successfully appended new chapter to course");
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

}
