// package com.calcmadeeasy;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.List;
// import java.util.UUID;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;

// import com.calcmadeeasy.dto.Chapters.ChapterResponseDTO;
// import com.calcmadeeasy.dto.Chapters.CreateChapterDTO;
// import com.calcmadeeasy.dto.Pages.CreatePageDTO;
// import com.calcmadeeasy.dto.Pages.PageResponseDTO;
// import com.calcmadeeasy.dto.Sections.CreateSectionDTO;
// import com.calcmadeeasy.dto.Sections.SectionResponseDTO;
// import com.calcmadeeasy.models.Chapters.Chapter;
// import com.calcmadeeasy.models.Courses.Course;
// import com.calcmadeeasy.models.Pages.Page;
// import com.calcmadeeasy.models.Problems.Problem;
// import com.calcmadeeasy.models.Sections.Section;
// import com.calcmadeeasy.models.Users.User;
// import com.calcmadeeasy.models.Users.UserProgress;
// import com.calcmadeeasy.services.ChapterService;
// import com.calcmadeeasy.services.CourseService;
// import com.calcmadeeasy.services.PageService;
// import com.calcmadeeasy.services.SectionService;
// import com.calcmadeeasy.services.UserProgressService;
// import com.calcmadeeasy.services.UserService;

// import jakarta.transaction.Transactional;

// @SpringBootTest
// @Transactional
// @ActiveProfiles("test")
// public class UserServiceTest {
//   @Autowired
//   private UserService userService;
//   @Autowired
//   private UserProgressService upService;
//   @Autowired
//   private CourseService courseServices;
//   @Autowired
//   private ChapterService chapterService;
//   @Autowired
//   private SectionService sectionService;
//   @Autowired
//   private PageService pageService;

//   private User user;
//   private UserProgress up;
//   private Course course;
//   private Chapter chapter;
//   private Section section;
//   private Page page;
//   private Problem problem;

//   @BeforeEach
//   public void setup() {

//     CreatePageDTO padto = new CreatePageDTO();
//     padto.setContent("CONTENT");
//     PageResponseDTO pageResponse = pageService.createPage(padto);
//     page = pageService.getPageEntity(pageResponse.getId());

//     CreateSectionDTO sdto = new CreateSectionDTO();
//     sdto.setDescription("DESCRIPTION");
//     sdto.setTitle("TITLE");
//     SectionResponseDTO sectionResponse = sectionService.createSection(sdto);
//     section = sectionService.getSectionEntity(sectionResponse.getId());
//     sectionService.addPage(section.getId(), page.getId());

//     CreateChapterDTO chdto = new CreateChapterDTO();
//     chdto.setDescription("CHAPTER DESCRIPTION");
//     chdto.setTitle("CHAPTER TITLE");
//     ChapterResponseDTO chapterResponse = chapterService.createChapter(chdto);
//     chapter = chapterService.getChapterEntity(chapterResponse.getId());
//     chapterService.addSection(chapter.getId(), section.getId());

//     course = Course.builder()
//         .description("description1")
//         .title("title1")
//         .chapters(chapter)
//         .build();
//     courseServices.createCourse(course);

//     user = User.builder()
//         .firstName("firstname")
//         .lastName("lastname")
//         .email("example@test.com")
//         .profilePicUrl("/test")
//         .build();
//     userService.createUserEntity(user);

//     // up = new UserProgress(user, page, problem);
//     // upService.createUserProgress(up);
//   }

//   // Create

//   @Test
//   public void testCreateUser() {
//     boolean exists = userService.exists(user.getId());
//     assertTrue(exists);
//   }

//   // Retrieval

//   @Test
//   public void testGetUser() {
//     UUID id = user.getId();
//     User u = userService.getUser(id);
//     boolean exists = userService.exists(id);
//     assertEquals(user, u, "Error: users do not match what was saved");
//     assertTrue(exists, "Error: user does not exists within DB");
//   }

//   @Test
//   public void testGetAllUsers() {
//     int size = userService.getAllUsers().size();

//     String err = "Error: number of users did not match expected";
//     assertEquals(1, size, err);
//   }

//   // Update

//   @Test
//   public void testEnrollNewCourse() {
//     // Arrange
//     int size = 1;
//     UUID uId = user.getId();
//     // Act
//     userService.enrollCourse(uId, course.getId());
//     int newSize = userService.getUser(uId).getCoursesEnrolled().size();

//     // Assert
//     String err = "Error: course was not but was expected";
//     assertEquals(size, newSize, "Error: number of courses did not match expected");
//     assertTrue(userService.getUser(uId).getCoursesEnrolled().contains(course), err);
//   }

//   // Remove

//   @Test
//   public void testUnenrollCourse() {
//     userService.enrollCourse(user.getId(), course.getId());

//     userService.unenrollCourse(user.getId(), course.getId());

//     boolean contains = userService.getUser(user.getId()).getCoursesEnrolled().contains(course);
//     int courseCount = userService.getUser(user.getId()).getCoursesEnrolled().size();
//     List<UserProgress> progressAfter = upService.getProgressForUserEntity(user.getId());

//     assertFalse(contains, "Error: course persisted after supposed removal");
//     assertEquals(0, courseCount, "Error: courses is not empty");
//     assertTrue(progressAfter.isEmpty(), "Error: progress was not removed when unenrolling course");
//   }
// }
