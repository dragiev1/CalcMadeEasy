package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Problems.ProblemSolutionType;
import com.calcmadeeasy.models.Problems.ProblemType;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.models.Users.UserProgress;
import com.calcmadeeasy.repository.ChapterRepo;
import com.calcmadeeasy.repository.CourseRepo;
import com.calcmadeeasy.repository.PageRepo;
import com.calcmadeeasy.repository.ProblemRepo;
import com.calcmadeeasy.repository.SectionRepo;
import com.calcmadeeasy.repository.UserProgressRepo;
import com.calcmadeeasy.repository.UserRepo;
import com.calcmadeeasy.services.UserProgressService;
import com.calcmadeeasy.services.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserProgessServiceTest {
    @Autowired UserService userService;
    @Autowired UserProgressService upService;

    private User user;
    private Page page;
    private Problem problem;
    private Section section;
    private Chapter chapter;
    private Course course;

    @Autowired UserRepo userRepo;
    @Autowired UserProgressRepo upRepo;
    @Autowired ProblemRepo problemRepo;
    @Autowired PageRepo pageRepo;
    @Autowired SectionRepo sectionRepo;
    @Autowired ChapterRepo chapterRepo;
    @Autowired CourseRepo courseRepo;

    @BeforeEach
    public void setup() {

        course = courseRepo.save(Course.builder()
            .title("COURSE TITLE")
            .description("COURSE DESCRIPTION")
            .build());

        chapter = Chapter.builder()
            .title("CHAPTER TITLE")
            .description("CHAPTER DESCRIPTION")
            .course(course) 
            .build();
        chapter = chapterRepo.save(chapter);

        section = Section.builder()
            .title("SECTION TITLE")
            .description("SECTION DESCRIPTION")
            .chapter(chapter) 
            .build();
        section = sectionRepo.save(section);

        problem = problemRepo.save(Problem.builder()
            .description("DESCRIPTION")
            .solution("SOLUTION")
            .points(3)
            .isChallenge(true)
            .solutionType(ProblemSolutionType.EXPRESSION)
            .build());

        page = Page.builder()
            .content("CONTENT")
            .position(1)
            .section(section) 
            .build();
        page.addProblem(problem, ProblemType.EXERCISE);
        page = pageRepo.save(page);

        user = User.builder()
            .firstName("FIRSTNAME")
            .lastName("LASTNAME")
            .email("TEST@EXAMPLE.COM")
            .profilePicUrl("/test.png")
            .build();
        user = userRepo.save(user);

        // Enroll mock user in mock course
        userService.enrollCourse(user.getId(), course.getId());
    }
    
    @Test
    public void testCreateUserProgress() {
        UserProgress up = UserProgress.builder()
            .attempts(0)
            .pointsEarned(0)
            .page(page)
            .problem(problem)
            .user(user)
            .solved(false)
            .build();

        upRepo.save(up);

        List<UserProgress> userProgressList = upRepo.findByUserId(user.getId());
        assertEquals(1, userProgressList.size(), "There should be exactly 1 user progress object for this user ID");

        UserProgress fetchedProgress = userProgressList.get(0);
        assertEquals(problem.getId(), fetchedProgress.getProblem().getId());
        assertEquals(page.getId(), fetchedProgress.getPage().getId());
        assertFalse(fetchedProgress.isSolved(), "New Progress should not be solved");
    }
    
    @Test
    public void testGetAllProgresses() {
        UserProgress up = UserProgress.builder()
        .attempts(0)
        .pointsEarned(0)
        .page(page)
        .problem(problem)
        .user(user)
        .solved(false)
        .build();
        upRepo.save(up);
        
        assertEquals(1, upService.getAllProgressDTO().size());
    }

    @Test
    public void testRecordProgress() {
        UserProgress up = UserProgress.builder()
            .attempts(0)
            .pointsEarned(0)
            .page(page)
            .problem(problem)
            .user(user)
            .solved(false)
            .build();
        upRepo.save(up);

        up.recordAttempt(true, problem);
        upRepo.save(up);

        UserProgress updatedUP = upRepo.findById(up.getId()).orElseThrow();
        assertEquals(1, updatedUP.getAttempts());
        assertTrue(updatedUP.isSolved(), "Progress should be marked as correct");
        assertEquals(3, updatedUP.getPointsEarned(), "Should've earned 3 points");
    }

    @Test
    public void testRemoveProgress() {
        UserProgress up = UserProgress.builder()
            .attempts(0)
            .pointsEarned(0)
            .page(page)
            .problem(problem)
            .user(user)
            .solved(false)
            .build();
        upRepo.save(up);

        up.recordAttempt(true, problem);

        upService.removeProgress(up.getId());
        assertFalse(upService.exists(up.getId()));
    }

    @Test
    public void testRemoveAllProgressByUserAndCourseId() {
        UserProgress inTarget = upRepo.save(UserProgress.builder()
            .attempts(0).pointsEarned(0)
            .page(page).problem(problem).user(user)
            .solved(false).build());

        Course otherCourse = courseRepo.save(Course.builder()
            .title("OTHER COURSE")
            .description("OTHER COURSE")
            .build());

        Chapter otherChapter = Chapter.builder()
            .title("OTHER CHAPTER")
            .description("OTHER CHAPTER")
            .course(otherCourse) 
            .build();
        otherChapter = chapterRepo.save(otherChapter);

        Section otherSection = Section.builder()
            .title("OTHER SECTION")
            .description("OTHER SECTION")
            .chapter(otherChapter) 
            .build();
        otherSection = sectionRepo.save(otherSection);

        Page otherPage = Page.builder()
            .content("OTHER")
            .position(1)
            .section(otherSection) 
            .build();
        otherPage.addProblem(problem, ProblemType.EXERCISE);
        otherPage = pageRepo.save(otherPage);

        UserProgress inOther = upRepo.save(UserProgress.builder()
            .attempts(0).pointsEarned(0)
            .page(otherPage).problem(problem).user(user)
            .solved(false).build());

        // Act
        upService.removeAllProgressByUserAndCourseId(user.getId(), course.getId());

        assertFalse(upRepo.existsById(inTarget.getId()), "Progress in the target course should have been deleted");
        assertTrue(upRepo.existsById(inOther.getId()), "Progress in an unrelated course must NOT be deleted");
    }

    @Test
    public void testRemoveAll() {
        UserProgress progress1 = upRepo.save(UserProgress.builder()
            .attempts(0).pointsEarned(0)
            .page(page).problem(problem).user(user)
            .solved(false).build());

        Course otherCourse = courseRepo.save(Course.builder()
            .title("OTHER COURSE")
            .description("OTHER COURSE")
            .build());

        Chapter otherChapter = Chapter.builder()
            .title("OTHER CHAPTER")
            .description("OTHER CHAPTER")
            .course(otherCourse) 
            .build();
        otherChapter = chapterRepo.save(otherChapter);

        Section otherSection = Section.builder()
            .title("OTHER SECTION")
            .description("OTHER SECTION")
            .chapter(otherChapter) 
            .build();
        otherSection = sectionRepo.save(otherSection);

        Page otherPage = Page.builder()
            .content("OTHER")
            .position(1)
            .section(otherSection) 
            .build();
        otherPage.addProblem(problem, ProblemType.EXERCISE);
        otherPage = pageRepo.save(otherPage);

        UserProgress progress2 = upRepo.save(UserProgress.builder()
            .attempts(0).pointsEarned(0)
            .page(otherPage).problem(problem).user(user)
            .solved(false).build());

        upService.removeAll(List.of(progress2.getId(), progress1.getId()));
        assertFalse(upService.exists(progress2.getId()));
        assertFalse(upService.exists(progress1.getId()));
    }
}