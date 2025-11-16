package com.calcmadeeasy;

import com.calcmadeeasy.dto.Problems.CreateProblemDTO;
import com.calcmadeeasy.dto.Problems.ProblemDTO;
import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Problems.ProblemSolutionType;
import com.calcmadeeasy.models.Tags.Tag;
import com.calcmadeeasy.services.ProblemServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    tag1 = new Tag("Tag1", Double.valueOf(0));
    tag2 = new Tag("Tag2", Double.valueOf(1));
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
        .solution("25")
        .tags(tag2)
        .build();
    problemService.createProblem(new CreateProblemDTO(problem1));
  }

  // CREATE

  @Test
  public void testCreateProblem() {
    boolean retrieved = problemService.exists(problem1.getId());

    assertEquals(true, retrieved, "Error: problem did not save");
    System.out.println("Successfully saved problem");
  }

  @Test
  public void testAddTag() {
    // Arrange
    Set<Tag> ogTags = new HashSet<>();
    ogTags.add(tag1);
    ogTags.add(tag2);
    Tag extraTag = new Tag("extra tag (addTag)", 0.5);

    // Act
    problemService.addTag(problem1, extraTag);

    // Assert
    assertNotEquals(ogTags, problem1.getTags(),
        "Error: original tags set matches problem's tag set when adding new tag");
    assertEquals(true, problem1.getTagById(extraTag.getId()).equals(extraTag),
        "Error: tag did not append to the problem");
    System.out.println("Successfully added tag to a problem");

  }

  // READ

  @Test
  public void testGetProblemById() {
    boolean exists = problemService.exists(problem1.getId());
    Problem retrieved = problemService.getProblemEntity(problem1.getId());

    assertEquals(true, exists, "Error: problem was not saved properly");
    assertEquals(retrieved, problem1, "Error: original problem and retrieved problem do not match");
    System.out.println("Successfully retrieved problem");
  }

  @Test
  public void testGetAllProblems() {
    problemService.createProblem(new CreateProblemDTO(problem2));
    int correctQuantity = 2;

    int retrievedQuantity = problemService.getAllProblemDTOs().size();
    List<ProblemDTO> retrieved = problemService.getAllProblemDTOs();

    assertEquals(retrieved.get(0).getId(), problem1.getId(), "Error: problem1 was not saved properly");
    assertEquals(retrieved.get(1).getId(), problem2.getId(), "Error: problem2 was not saved properly");
    assertEquals(correctQuantity, retrievedQuantity, "Error: quantity of problems do not match");
    System.out.println("Successfully retrieved all problems");
  }

  // UPDATE

  // TODO: Test new updateProblem() service method

  // DELETION

  @Test
  public void testDeleteProblem() {
    UUID pId = problem1.getId();

    problemService.deleteProblem(pId);
    boolean retrieved = problemService.exists(pId);

    assertEquals(false, retrieved, "Error: problem still exists after deletion");
    System.out.println("Successfully deleted a problem");
  }

  @Test
  public void testDeleteTagFromProblem() {
    // Arrange
    Tag extraTag = new Tag("extra tag (addTag)", 0.5);

    // Act
    problemService.addTag(problem1, extraTag);
    Set<Tag> retrieved = problemService.getProblemEntity(problem1.getId()).getTags();
    assertEquals(true, retrieved.contains(extraTag), "Error: Tag never appended to the problem's tag set");
    retrieved.remove(extraTag);

    // Assert
    assertEquals(false, retrieved.contains(extraTag), "Error: Tag exists after deletion");
    System.out.println("Successfully removed Tag from Problem");
  }
}
