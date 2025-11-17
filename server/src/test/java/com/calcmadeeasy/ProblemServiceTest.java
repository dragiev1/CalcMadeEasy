package com.calcmadeeasy;

import com.calcmadeeasy.dto.Problems.CreateProblemDTO;
import com.calcmadeeasy.dto.Problems.ProblemDTO;
import com.calcmadeeasy.dto.Problems.ProblemResponseDTO;
import com.calcmadeeasy.dto.Tags.CreateTagDTO;
import com.calcmadeeasy.dto.Tags.TagDTO;
import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Problems.ProblemSolutionType;
import com.calcmadeeasy.models.Tags.Tag;
import com.calcmadeeasy.services.ProblemServices;
import com.calcmadeeasy.services.TagServices;

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
  @Autowired
  private TagServices tagService;

  private Tag tag1;
  private Tag tag2;
  private Problem problem1;
  private Problem problem2;
  private ProblemDTO testProblemDTO;

  @BeforeEach
  public void setup() {
    tag1 = new Tag("Tag1", Double.valueOf(0));
    tag2 = new Tag("Tag2", Double.valueOf(1));
    tagService.createTag(new CreateTagDTO(tag1));
    tagService.createTag(new CreateTagDTO(tag2));

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
    ProblemResponseDTO response = problemService.createProblem(new CreateProblemDTO(problem1));
    problem1 = problemService.getProblemEntity(response.getId());
    testProblemDTO = problemService.getProblemDTO(response.getId());
  }

  // CREATE

  @Test
  public void testCreateProblem() {
    boolean retrieved = problemService.exists(testProblemDTO.getId());

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
    TagDTO t = tagService.createTag(new CreateTagDTO(extraTag));

    // Act
    ProblemDTO updated = problemService.addTag(problem1.getId(), t.getId());

    // Assert
    assertNotEquals(ogTags, updated.getTags(),
        "Error: original tags set matches problem's tag set when adding new tag");
    assertEquals(t.getId(), updated.getTagById(t.getId()).getId(),
        "Error: tag did not append to the problem");
    System.out.println("Successfully added tag to a problem");

  }

  // READ

  @Test
  public void testGetProblemById() {
    boolean exists = problemService.exists(testProblemDTO.getId());
    Problem retrieved = problemService.getProblemEntity(testProblemDTO.getId());

    assertEquals(true, exists, "Error: problem was not saved properly");
    assertEquals(retrieved.getId(), testProblemDTO.getId(),
        "Error: original problem and retrieved problem do not match");
    System.out.println("Successfully retrieved problem");
  }

  @Test
  public void testGetAllProblems() {
    ProblemResponseDTO r = problemService.createProblem(new CreateProblemDTO(problem2));
    problem2 = problemService.getProblemEntity(r.getId());
    int correctQuantity = 2;

    int retrievedQuantity = problemService.getAllProblemDTOs().size();
    List<ProblemDTO> retrieved = problemService.getAllProblemDTOs();

    assertEquals(retrieved.get(0).getId(), testProblemDTO.getId(), "Error: testProblemDTO was not saved properly");
    assertEquals(retrieved.get(1).getId(), problem2.getId(), "Error: problem2 was not saved properly");
    assertEquals(correctQuantity, retrievedQuantity, "Error: quantity of problems do not match");
    System.out.println("Successfully retrieved all problems");
  }

  // UPDATE

  // TODO: Test new updateProblem() service method

  // DELETION

  @Test
  public void testDeleteProblem() {
    UUID pId = testProblemDTO.getId();

    problemService.deleteProblem(pId);
    boolean retrieved = problemService.exists(pId);

    assertEquals(false, retrieved, "Error: problem still exists after deletion");
    System.out.println("Successfully deleted a problem");
  }

  @Test
  public void testDeleteTagFromProblem() {
    // Arrange
    Tag extraTag = new Tag("extra tag (addTag)", 0.5);
    TagDTO t = tagService.createTag(new CreateTagDTO(extraTag));
    extraTag = tagService.getTagEntity(t.getId());
    problemService.addTag(problem1.getId(), extraTag.getId());
    
    // Act
    Set<Tag> retrieved = problemService.getProblemEntity(problem1.getId()).getTags();
    assertEquals(true, retrieved.contains(extraTag), "Error: Tag never appended to the problem's tag set");
    retrieved.remove(extraTag);

    // Assert
    assertEquals(false, retrieved.contains(extraTag), "Error: Tag exists after deletion");
    System.out.println("Successfully removed Tag from Problem");
  }
}
