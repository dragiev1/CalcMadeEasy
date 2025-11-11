package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problem.Problem;
import com.calcmadeeasy.models.Problem.ProblemSolutionType;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.models.Users.UserProgress;
import com.calcmadeeasy.services.PageServices;
import com.calcmadeeasy.services.ProblemServices;
import com.calcmadeeasy.services.UserProgressService;
import com.calcmadeeasy.services.UserServices;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserProgressServiceTest {
  @Autowired
  private UserProgressService upService;
  @Autowired
  private UserServices userService;
  @Autowired
  private ProblemServices problemService;
  @Autowired
  private PageServices pageService;

  private UserProgress up;
  private User user;
  private Problem problem;
  private Page page;

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

    user = User.builder()
        .email("test@example.com")
        .firstName("firstname")
        .lastName("lastname")
        .profilePicUrl("/")
        .build();
    userService.createUser(user);

    up = new UserProgress(user, page, problem);
    upService.createUserProgress(up);
  }

  @Test
  public void testCreateUserProgress() {
    boolean exists = upService.exists(up.getId());
    assertTrue(exists);
  }

  @Test
  public void testGetProgressForUser() {
    int size = upService.getProgressForUser(user.getId()).size();

    assertEquals(1, size);
    assertTrue(upService.getProgressForUser(user.getId()).stream().allMatch(p -> p.getUser().equals(user)));
  }

  @Test
  public void testRecordAttempt() {
    up.recordAttempt(true, problem);

    UserProgress updated = upService.getUserProgress(up.getId());
    boolean updatedSolved = updated.isSolved();
    int pointsEarned = updated.getPointsEarned();

    assertTrue(updatedSolved);
    assertEquals(3, pointsEarned);
  }

  @Test
  public void testResetProgress() {
    // Arrange
    upService.recordAttempt(up.getId(), true, problem);
    if (!upService.getUserProgress(up.getId()).isSolved())
      throw new IllegalArgumentException("Error: expected isSolved to be true, but was false");

    // Act
    upService.resetProgress(up.getId());
    UserProgress updated = upService.getUserProgress(up.getId());
    boolean isSolved = updated.isSolved();
    int points = updated.getPointsEarned();
    int attempts = updated.getAttempts();
    
    // Assert
    assertFalse(isSolved);
    assertEquals(0, points);
    assertEquals(0, attempts);
  }
}
