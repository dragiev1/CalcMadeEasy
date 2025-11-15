package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.calcmadeeasy.dto.Tags.CreateTagDTO;
import com.calcmadeeasy.dto.Tags.TagDTO;
import com.calcmadeeasy.models.Tags.Tag;
import com.calcmadeeasy.services.TagServices;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class TagServiceTest {

  @Autowired
  private TagServices tagServices;

  private Tag tag;
  private TagDTO newTag;

  @BeforeEach
  public void setup() {
    tag = new Tag("test (createTag)", 0.9);
    newTag = tagServices.createTag(new CreateTagDTO(tag));
  }

  // Create

  @Test
  public void testCreateTag() {
    boolean exists = tagServices.exists(newTag.getId());
    assertEquals(exists, true, "Tag does not exist");
    System.out.println("Successfully created tag");
  }

  @Test
  public void testCreateTags() {
    Tag tag1 = new Tag("test1 (createTags)", 0.9);
    Tag tag2 = new Tag("test2 (createTags)", 0.9);

    tagServices.createTags(tag1, tag2);
    boolean exists1 = tagServices.exists(tag1.getId());
    boolean exists2 = tagServices.exists(tag2.getId());

    assertEquals(exists1, true, "Tag1 does not exist");
    assertEquals(exists2, true, "Tag2 does not exist");
    System.out.println("Successfully created multiple tags");
  }

  // Retrevial

  @Test
  public void testGetAllTags() {
    Tag tag2 = new Tag("test2 (getAllTags)", 0.1);
    TagDTO t2 = tagServices.createTag(new CreateTagDTO(tag2));
    List<TagDTO> ogTags = List.of(newTag, t2);
    List<TagDTO> tags = tagServices.getAllTags();

    assertEquals(ogTags.get(0).getId(), tags.get(0).getId(), "Error: original tag list does not match with list from database");
    assertEquals(ogTags.get(1).getId(), tags.get(1).getId(), "Error: original tag list does not match with list from database");
    System.out.println("Succesfully retrieved all tags");
  }

  @Test
  public void testGetTag() {
    Tag tag = new Tag("test (getTagById)", 0.5);
    TagDTO saved = tagServices.createTag(new CreateTagDTO(tag));
    Tag retrieved = tagServices.getTagEntity(saved.getId());
    assertEquals(saved.getId(), retrieved.getId());
    System.out.println("Successfully retreived page");
  }

  @Test
  public void testGetTagsByDifficultyRange() {
    Tag tag1 = new Tag("test1 (GetTagsByDifficultyRange)", 0.4);
    Tag tag2 = new Tag("test2 (GetTagsByDifficultyRange)", 0.1);
    Tag tag3 = new Tag("test3 (GetTagsByDifficultyRange)", 0.5);
    Tag tag4 = new Tag("test4 (GetTagsByDifficultyRange)", 0.95);
    List<Tag> og = List.of(tag1, tag2, tag3, tag4);
    tagServices.createTags(og);
    List<Tag> correct = List.of(tag1, tag2, tag3);

    List<Tag> retrieved = tagServices.getTagsByDifficultyRange(0, 0.5);

    assertEquals(correct, retrieved, "Error: original tag list does not equate to tags");
    System.out.println("Successfully retrieved correct list in respect to difficulty bounds inputted");
  }

  // Update

  @Test
  public void testUpdateDifficultyById() {
    // Arrange
    Tag tag = new Tag("test (updateDifficultyById)", 0.5);
    double ogDiff = tag.getDifficulty();
    String ogName = tag.getTagName();
    TagDTO request = new TagDTO(new Tag("changed", 0.3));
    
    // Act
    TagDTO retrieved = tagServices.updateTag(newTag.getId(), request);
    double newDiff = retrieved.getDifficulty();
    String newName = retrieved.getTagName();

    // Assert
    assertNotEquals(ogDiff, newDiff);
    assertNotEquals(ogName, newName);
    assertEquals(0.3, newDiff,
        "Error: difficulty does not match expected");
    assertEquals("changed", newName,
        "Error: name does not match expected");
    System.out.println("Successfully updated difficulty on an existing tag");
  }

  // Delete

  @Test
  public void testDeleteTagById() {
    // Act
    tagServices.deleteTagById(newTag.getId());
    boolean exists = tagServices.exists(newTag.getId());

    // Assert
    assertEquals(false, exists, "Error: tag was not deleted");
    System.out.println("Successfully deleted tag");
  }
}
