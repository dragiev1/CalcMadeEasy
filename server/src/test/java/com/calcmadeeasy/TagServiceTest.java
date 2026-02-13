package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.calcmadeeasy.dto.Tags.CreateTagDTO;
import com.calcmadeeasy.dto.Tags.TagDTO;
import com.calcmadeeasy.dto.Tags.UpdateTagDTO;
import com.calcmadeeasy.models.Tags.Tag;
import com.calcmadeeasy.services.TagServices;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class TagServiceTest {

  @Autowired
  private TagServices tagServices;

  private TagDTO tagDTO;

  @BeforeEach
  public void setup() {
    CreateTagDTO dto = new CreateTagDTO();
    dto.setDifficulty(0.9);
    dto.setTagName("NAME");
    tagDTO = tagServices.createTag(dto);
  }

  // Create

  @Test
  public void testCreateTag() {
    boolean exists = tagServices.exists(tagDTO.getId());
    assertTrue(exists, "Tag does not exist");
  }

  // Read

  @Test
  public void testGetAllTags() {
    List<TagDTO> ogTags = List.of(tagDTO);
    List<TagDTO> tags = tagServices.getAllTags();

    assertEquals(ogTags.get(0).getId(), tags.get(0).getId(), "Error: original tag list does not match with list from database");
  }

  @Test
  public void testGetTag() {
    Tag retrieved = tagServices.getTagEntity(tagDTO.getId());

    assertEquals(tagDTO.getId(), retrieved.getId());
  }

  @Test
  public void testGetTagsByDifficultyRange() {
    List<Tag> retrieved = tagServices.getTagsByDifficultyRange(Double.valueOf(0), Double.valueOf(0.5));

    assertTrue(retrieved.isEmpty());
  }

  // Update

  @Test
  public void testUpdateTag() {
    Double ogDiff = tagDTO.getDifficulty();
    String ogName = tagDTO.getTagName();
    UpdateTagDTO update = new UpdateTagDTO();
    update.setDifficulty(0.0);
    update.setTagName("CHANGED");

    TagDTO retrieved = tagServices.updateTag(tagDTO.getId(), update);
    Double newDiff = retrieved.getDifficulty();
    String newName = retrieved.getTagName();

    assertNotEquals(ogDiff, newDiff);
    assertNotEquals(ogName, newName);
    assertEquals(0.0, newDiff,
        "Error: difficulty does not match expected");
    assertEquals("CHANGED", newName,
        "Error: tag name does not match expected");
  }

  // Delete

  @Test
  public void testRemoveTagById() {
    // Act
    tagServices.removeTagById(tagDTO.getId());
    boolean exists = tagServices.exists(tagDTO.getId());

    // Assert
    assertFalse(exists, "Error: tag was not removed");
  }
}
