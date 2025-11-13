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

import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problem.Problem;
import com.calcmadeeasy.models.Problem.ProblemSolutionType;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.models.Users.UserProgress;
import com.calcmadeeasy.services.ChapterServices;
import com.calcmadeeasy.services.CourseServices;
import com.calcmadeeasy.services.PageServices;
import com.calcmadeeasy.services.ProblemServices;
import com.calcmadeeasy.services.SectionServices;
import com.calcmadeeasy.services.UserProgressService;
import com.calcmadeeasy.services.UserServices;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserServiceTest {
  @Autowired
  private UserServices userServices;
  @Autowired
  private UserProgressService upService;
  @Autowired
  private CourseServices courseServices;
  @Autowired
  private ChapterServices chapterService;
  @Autowired
  private SectionServices sectionService;
  @Autowired
  private PageServices pageService;
  @Autowired
  private ProblemServices problemService;

  private User user;
  private UserProgress up;
  private Course course;
  private Chapter chapter;
  private Section section;
  private Page page;
  private Problem problem;

  @BeforeEach
  public void setup() {
    problem = Problem.builder()
        .description("description")
        .points(3)
        .solution("solution")
        .isChallenge(false)
        .solutionType(ProblemSolutionType.EXPRESSION)
        .build();
    problemService.createProblem(problem);

    page = Page.builder()
        .content("content")
        .build();

    pageService.createPage(page);

    section = Section.builder()
        .description("description")
        .pages(page)
        .title("title")
        .build();
    sectionService.createSection(section);

    chapter = Chapter.builder()
        .description("description")
        .title("title")
        .sections(section)
        .build();
    chapterService.createChapter(chapter);

    course = Course.builder()
        .description("description1")
        .title("title1")
        .chapters(chapter)
        .build();
    courseServices.createCourse(course);

    user = User.builder()
        .firstName("firstname")
        .lastName("lastname")
        .email("example@test.com")
        .profilePicUrl("/test")
        .build();
    userServices.createUserEntity(user);

    up = new UserProgress(user, page, problem);
    upService.createUserProgress(up);
  }

  // Create

  @Test
  public void testCreateUser() {
    boolean exists = userServices.exists(user.getId());
    assertTrue(exists);
  }

  // Retrieval

  @Test
  public void testGetUser() {
    UUID id = user.getId();
    User u = userServices.getUser(id);
    boolean exists = userServices.exists(id);
    assertEquals(user, u, "Error: users do not match what was saved");
    assertTrue(exists, "Error: user does not exists within DB");
  }

  @Test
  public void testGetAllUsers() {
    int size = userServices.getAllUsers().size();

    String err = "Error: number of users did not match expected";
    assertEquals(1, size, err);
  }

  // Update

  @Test
  public void testEnrollNewCourse() {
    // Arrange
    int size = 1;
    UUID uId = user.getId();
    // Act
    userServices.enrollCourse(uId, course.getId());
    int newSize = userServices.getUser(uId).getCourses().size();

    // Assert
    String err = "Error: course was not but was expected";
    assertEquals(size, newSize, "Error: number of courses did not match expected");
    assertTrue(userServices.getUser(uId).getCourses().contains(course), err);
  }

  // Remove

  @Test
  public void testUnenrollCourse() {
    userServices.enrollCourse(user.getId(), course.getId());

    userServices.unenrollCourse(user.getId(), course.getId());

    boolean contains = userServices.getUser(user.getId()).getCourses().contains(course);
    int courseCount = userServices.getUser(user.getId()).getCourses().size();
    List<UserProgress> progressAfter = upService.getProgressForUser(user.getId());

    assertFalse(contains, "Error: course persisted after supposed removal");
    assertEquals(0, courseCount, "Error: courses is not empty");
    assertTrue(progressAfter.isEmpty(), "Error: progress was not deleted when unenrolling course");
  }
}
