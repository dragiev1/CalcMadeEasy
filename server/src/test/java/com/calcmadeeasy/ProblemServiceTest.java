package com.calcmadeeasy;

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
        .solution("25")
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

  // READ

  @Test
  public void testGetProblemById() {
    problemService.createProblem(problem1);
    boolean exists = problemService.exists(problem1.getId());
    Problem retrieved = problemService.getProblemById(problem1.getId());

    assertEquals(true, exists, "Error: problem was not saved properly");
    assertEquals(retrieved, problem1, "Error: original problem and retrieved problem do not match");
    System.out.println("Successfully retrieved problem");
  }

  @Test
  public void testGetAllProblems() {
    problemService.createProblems(problem1, problem2);
    int correctQuantity = 2;

    int retrievedQuantity = problemService.getAllProblems().size();
    List<Problem> retrieved = problemService.getAllProblems();

    assertEquals(retrieved.get(0).getId(), problem1.getId(), "Error: problem1 was not saved properly");
    assertEquals(retrieved.get(1).getId(), problem2.getId(), "Error: problem2 was not saved properly");
    assertEquals(correctQuantity, retrievedQuantity, "Error: quantity of problems do not match");
    System.out.println("Successfully retrieved all problems");
  }

  // UPDATE

  @Test
  public void testUpdateDescription() {
    String ogDescription = problem1.getDescription();
    problemService.createProblem(problem1);
    problem1.setDescription("CHANGED");

    String retrieved = problemService.getProblemById(problem1.getId()).getDescription();

    assertNotEquals(ogDescription, retrieved, "Error: description was not updated or saved");
    assertEquals("CHANGED", retrieved);
    System.out.println("Successfully updated problem description");
  }

  @Test
  public void testUpdateSolution() {
    String ogSolution = problem1.getSolution();
    problemService.createProblem(problem1);

    problemService.updateSolution(problem1.getId(), "CHANGED SOLUTION");
    String newSolution = problemService.getProblemById(problem1.getId()).getSolution();

    assertEquals("CHANGED SOLUTION", newSolution, "Error: new solution does not match with expected");
    assertNotEquals(ogSolution, newSolution, "Error: new solution matches old solution, did not update");
    System.out.println("Successfully updated problem solution");
  }

  @Test
  public void testUpdateSolutionType() {
    ProblemSolutionType ogSolutionType = problem1.getSolutionType();
    problemService.createProblem(problem1);

    problemService.updateSolutionType(problem1.getId(), ProblemSolutionType.NUMERICAL);
    ProblemSolutionType newSolutionType = problemService.getProblemById(problem1.getId()).getSolutionType();

    String error_msg = "Error: solution type did not update";
    assertEquals(ProblemSolutionType.NUMERICAL, newSolutionType, error_msg);
    assertNotEquals(ogSolutionType, newSolutionType, error_msg);
    System.out.println("Successfully updated solution type");

  }

  @Test
  public void testUpdateIsChallenge() {
    boolean og = problem1.getIsChallenge();
    problemService.createProblem(problem1);

    problemService.updateIsChallenge(problem1.getId(), !og);
    boolean inverse = problemService.getProblemById(problem1.getId()).getIsChallenge();

    String error_msg = "Error: challenge boolean was not updated properly";
    assertNotEquals(og, inverse, error_msg);
    System.out.println("Successfully updated problem to challenge problem");
  }

  @Test
  public void testUpdatePoints() {
    int ogPoints = problem1.getPoints();
    problemService.createProblem(problem1);

    problemService.updatePoints(problem1.getId(), 10);
    int newPoints = problemService.getProblemById(problem1.getId()).getPoints();

    assertNotEquals(ogPoints, newPoints);
    assertEquals(10, newPoints);
    System.out.println("Successfully updated points");
  }

  // DELETION

  @Test
  public void testDeleteProblem() {
    problemService.createProblem(problem1);
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
    problemService.createProblem(problem1);

    // Act
    problemService.addTag(problem1, extraTag);
    Set<Tag> retrieved = problemService.getProblemById(problem1.getId()).getTags();
    assertEquals(true, retrieved.contains(extraTag), "Error: Tag never appended to the problem's tag set");
    retrieved.remove(extraTag);

    // Assert
    assertEquals(false, retrieved.contains(extraTag), "Error: Tag exists after deletion");
    System.out.println("Successfully removed Tag from Problem");
  }
}
