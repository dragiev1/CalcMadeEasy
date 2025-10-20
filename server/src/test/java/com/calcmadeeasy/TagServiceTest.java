package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.calcmadeeasy.models.Tags.Tag;
import com.calcmadeeasy.services.TagServices;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class TagServiceTest {

  @Autowired
  private TagServices tagServices;

  // Create

  @Test
  public void testCreateTag() {
    Tag tag = new Tag("test (createTag)", 0.9);
    tagServices.createTag(tag);
    boolean exists = tagServices.exists(tag.getId());
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
    Tag tag1 = new Tag("test1 (getAllTags)", 0.1);
    Tag tag2 = new Tag("test2 (getAllTags)", 0.1);
    List<Tag> ogTags = List.of(tag1, tag2);

    tagServices.createTags(tag1, tag2);
    List<Tag> tags = tagServices.getAllTags();

    assertEquals(ogTags, tags, "Error: original tag list does not match with list from database");
    System.out.println("Succesfully retrieved all tags");
  }

  @Test
  public void testGetTagById() {
    Tag tag = new Tag("test (getTagById)", 0.5);
    Tag saved = tagServices.createTag(tag);
    Tag retrieved = tagServices.getTagById(tag.getId());
    assertEquals(saved, retrieved);
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
    double og = tag.getDifficulty();

    tagServices.createTag(tag);

    // Act
    tagServices.updateDifficultyById(tag.getId(), 0.3);
    Tag retrieved = tagServices.getTagById(tag.getId());
    double newDiff = retrieved.getDifficulty();

    // Assert
    assertNotEquals(og, newDiff);
    assertEquals(0.3, newDiff,
        "Error: difficulty does not match expected");
    System.out.println("Successfully updated difficulty on an existing tag");
  }

  @Test
  public void testUpdateNameById() {
    // Arrange
    Tag tag = new Tag("test (updateNameById)", 0.6);
    String og = tag.getTagName();

    tagServices.createTag(tag);

    // Act
    tagServices.updateNameById(tag.getId(), "CHANGED");
    Tag retreived = tagServices.getTagById(tag.getId());
    String newName = retreived.getTagName();

    // Assert
    assertNotEquals(og, newName, "Error: tag name did not update properly");
    assertEquals("CHANGED", newName,
        "Error: expected value does not equate");
    System.out.println("Succesfully updated tag name on existing tag");
  }

  // Delete

  @Test
  public void testDeleteTagById() {
    // Arrange
    Tag tag = new Tag("test (deleteTagById)", 0.3);
    tagServices.createTag(tag);

    // Act
    tagServices.deleteTagById(tag.getId());
    boolean exists = tagServices.exists(tag.getId());

    // Assert
    assertEquals(false, exists, "Error: tag was not deleted");
    System.out.println("Successfully deleted tag");

  }
}
