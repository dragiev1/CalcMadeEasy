package com.calcmadeeasy;

import com.calcmadeeasy.dto.Problems.CreateProblemDTO;
import com.calcmadeeasy.dto.Problems.ProblemDTO;
import com.calcmadeeasy.dto.Problems.ProblemResponseDTO;
import com.calcmadeeasy.dto.Problems.UpdateProblemDTO;
import com.calcmadeeasy.dto.Tags.CreateTagDTO;
import com.calcmadeeasy.dto.Tags.TagDTO;
import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Problems.ProblemSolutionType;
import com.calcmadeeasy.models.Tags.Tag;
import com.calcmadeeasy.services.ProblemServices;
import com.calcmadeeasy.services.TagServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  private CreateTagDTO tag1;
  private CreateTagDTO tag2;
  private Tag tagEntity1;
  private Problem problem1;
  private ProblemDTO testProblemDTO;

  @BeforeEach
  public void setup() {
    CreateTagDTO tag1 = new CreateTagDTO();
    tag1.setTagName("Tag1");
    tag1.setDifficulty(Double.valueOf(0));
    TagDTO tag1DTO = tagService.createTag(tag1);
    tagEntity1 = tagService.getTagEntity(tag1DTO.getId());

    CreateProblemDTO dto = new CreateProblemDTO();
    dto.setDescription("DESCRIPTION");
    dto.setIsChallenge(false);
    dto.setSolution("SOLUTION");
    dto.setSolutionType(ProblemSolutionType.EXPRESSION);
    dto.setPoints(0);
    ProblemResponseDTO response = problemService.createProblem(dto);
    problem1 = problemService.getProblemEntity(response.getId());
    testProblemDTO = problemService.getProblemDTO(response.getId());

  }

  // CREATE

  @Test
  public void testCreateProblem() {
    boolean retrieved = problemService.exists(testProblemDTO.getId());

    assertTrue(retrieved, "Error: problem did not save");
  }

  @Test
  public void testAddTag() {
    // Arrange
    
    Set<CreateTagDTO> ogTags = new HashSet<>();
    ogTags.add(tag1);
    ogTags.add(tag2);

    // Act
    ProblemDTO updated = problemService.addTag(problem1.getId(), tagEntity1.getId());

    // Assert
    assertNotEquals(ogTags, updated.getTags(),
        "Error: old tag set matches new tag set when adding new tag");
    assertEquals(tagEntity1.getId(), updated.getTagById(tagEntity1.getId()).getId(),
        "Error: tag did not append to the problem");

  }

  // READ

  @Test
  public void testGetProblemById() {
    boolean exists = problemService.exists(testProblemDTO.getId());
    Problem retrieved = problemService.getProblemEntity(testProblemDTO.getId());

    assertEquals(true, exists, "Error: problem was not saved properly");
    assertEquals(retrieved.getId(), testProblemDTO.getId(),
        "Error: original problem and retrieved problem do not match");
  }

  @Test
  public void testGetAllProblems() {

    int correctQuantity = 1;

    int retrievedQuantity = problemService.getAllProblemDTOs().size();
    List<ProblemDTO> retrieved = problemService.getAllProblemDTOs();

    assertEquals(retrieved.get(0).getId(), problem1.getId(), "Error: problem1 was not saved properly");
    assertEquals(correctQuantity, retrievedQuantity, "Error: quantity of problems do not match");
  }

  // UPDATE

  @Test
  public void testUpdateProblem() {
    UpdateProblemDTO update = new UpdateProblemDTO();
    update.setDescription("CHANGED");
    update.setIsChallenge(true);
    update.setSolutionType(ProblemSolutionType.NUMERICAL);
    update.setPoints(100);
    update.setSolution("CHANGED");

    ProblemResponseDTO updated = problemService.updateProblem(problem1.getId(), update);
    Problem p = problemService.getProblemEntity(updated.getId());

    assertTrue(p.getIsChallenge());
    assertEquals(p.getDescription(), p.getSolution(), "Error: description or solution was not updated correct");
    assertEquals(p.getSolutionType(), ProblemSolutionType.NUMERICAL, "Error: solution type was not updated");
    assertEquals(p.getPoints(), 100, "Error: points was not updated");
  }  

  // DELETION

  @Test
  public void testRemoveProblem() {
    UUID pId = testProblemDTO.getId();

    problemService.removeProblem(pId);
    boolean retrieved = problemService.exists(pId);

    assertEquals(false, retrieved, "Error: problem still exists after deletion");
  }

  @Test
  public void testRemoveTagFromProblem() {
    // Arrange
    problemService.addTag(problem1.getId(), tagEntity1.getId());

    // Act
    Set<Tag> retrieved = problemService.getProblemEntity(problem1.getId()).getTags();
    assertEquals(true, retrieved.contains(tagEntity1), "Error: Tag never appended to the problem's tag set");
    retrieved.remove(tagEntity1);

    // Assert
    assertEquals(false, retrieved.contains(tagEntity1), "Error: Tag exists after deletion");
  }
}
