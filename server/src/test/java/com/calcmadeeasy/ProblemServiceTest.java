package com.calcmadeeasy;

import com.calcmadeeasy.models.Problem.Problem;
import com.calcmadeeasy.models.Problem.ProblemType;
import com.calcmadeeasy.models.Tags.Tag;
import com.calcmadeeasy.models.Problem.ProblemSolutionType;
import com.calcmadeeasy.services.ProblemServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ProblemServiceTest {
  @Autowired
  private ProblemServices problemService;

  private Tag tag1;
  private Tag tag2;
  private Problem problem1;
  private Problem problem2;

  @BeforeEach
  public void setup() {
    tag1 = new Tag("Tag1", 0);
    tag2 = new Tag("Tag2", 1);
    problem1 = Problem.builder()
        .description("description1 (getAllProblems)")
        .isChallenge(false)
        .points(3)
        .solutionType(ProblemSolutionType.EXPRESSION)
        .solution("solution1 (getAllProblems)")
        .tags(tag1, tag2)
        .build();
    problem2 = Problem.builder()
        .description("description2 (getAllProblems)")
        .isChallenge(true)
        .points(1)
        .solutionType(ProblemSolutionType.NUMERICAL)
        .solution("solution2 (getAllProblems)")
        .tags(tag2)
        .build();
  }

  // CREATE

  @Test
  public void testCreateProblem() {
    problemService.createProblem(problem1);
    boolean retrieved = problemService.exists(problem1.getId());

    assertEquals(true, retrieved, "Error: problem did not save");
    System.out.println("Successfully saved problem");
  }

  @Test
  public void testCreateProblems() {
    problemService.createProblems(problem1, problem2);
    boolean retrievedP1 = problemService.exists(problem1.getId());
    boolean retrievedP2 = problemService.exists(problem2.getId());

    assertEquals(true, retrievedP1, "Error: problem1 was not saved properly.");
    assertEquals(true, retrievedP2, "Error: problem2 was not saved properly.");
    System.out.println("Successfully");
  }

  @Test
  public void testAddTag() {
    // Arrange
    Set<Tag> ogTags = new HashSet<>();
    ogTags.add(tag1);
    ogTags.add(tag2);
    Tag extraTag = new Tag("extra tag (addTag)", 0.5);
    problemService.createProblem(problem1);

    // Act
    problemService.addTag(problem1, extraTag);

    // Assert
    assertNotEquals(ogTags, problem1.getTags(),
        "Error: original tags set matches problem's tag set when adding new tag");
    assertEquals(true, problem1.getTagById(extraTag.getId()).equals(extraTag),
        "Error: tag did not append to the problem");
    System.out.println("Successfully added tag to a problem");

  }

  // RETRIEVAL
}
