package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.services.SectionServices;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class SectionServiceTest {

  @Autowired
  private SectionServices sectionService;

  private Page page1;
  private Page page2;
  private Section section1;
  private Section section2;

  @BeforeEach
  public void setup() {
    page1 = Page.builder()
        .content("content (test)")
        .build();
    page2 = Page.builder()
        .content("content2 (test)")
        .build();
    section1 = Section.builder()
        .description("description (test)")
        .title("title (test)")
        .pages(page1)
        .build();
    section2 = Section.builder()
        .description("description2 (test)")
        .title("title2 (test)")
        .pages(page2)
        .build();
    sectionService.createSection(section1);
    sectionService.createSection(section2);
  }

  // CREATE

  @Test
  public void testCreateSection() {
    sectionService.createSection(section1);
    boolean exists = sectionService.exists(section1.getId());
    String err = "Section was not saved";
    assertEquals(true, exists, err);
  }

  @Test
  public void testCreateSections() {
    sectionService.createSections(section1, section2);

    boolean retrieved1 = sectionService.exists(section1.getId());
    boolean retrieved2 = sectionService.exists(section2.getId());

    String err = "Section was not saved";
    assertEquals(true, retrieved1, err);
    assertEquals(true, retrieved2, err);
    System.out.println("Successfully retrieved section");
  }

  @Test
  public void testAddPage() {
    // Arrange
    List<Page> ogPages = new ArrayList<>();
    ogPages.add(page2);

    sectionService.createSection(section2);

    // Act
    sectionService.addPage(section2, page1);

    // Assert

    int size = sectionService.getAllSections().size();
    String err = "Error: section was not saved properly";
    assertNotEquals(ogPages.size(), size);
    assertEquals(true, sectionService.getSectionById(section2.getId()).getPages().contains(page1), err);
    assertEquals(true, sectionService.getSectionById(section2.getId()).getPages().contains(page2), err);
    System.out.println("Successfully added a new page to a section");
  }

  // READ

  @Test
  public void testGetAllPages() {
    sectionService.createSections(section1, section2);

    int size = sectionService.getAllSections().size();
    boolean retrieved1 = sectionService.exists(section1.getId());
    boolean retrieved2 = sectionService.exists(section2.getId());

    String err = "Error: section(s) was not saved properly ";
    assertEquals(2, size, err);
    assertEquals(true, retrieved1, err);
    assertEquals(true, retrieved2, err);
    System.out.println("Successfully retrieved all sections");
  }

  // UPDATE

  @Test
  public void testUpdateDescription() {
    sectionService.createSection(section1);
    String changed = "CHANGED";

    sectionService.updateDescription(section1.getId(), changed);

    String err = "Error: description was not updated";
    assertEquals(changed, sectionService.getSectionById(section1.getId()).getDescription(), err);
    System.out.println("Successfully updated description");
  }

  @Test
  public void testUpdateTitle() {
    // Arrange
    sectionService.createSection(section1);
    String changed = "CHANGED";

    // Act
    sectionService.updateTitle(section1.getId(), changed);
    String retrieved = sectionService.getSectionById(section1.getId()).getTitle();

    // Assert
    String err = "Error: title did not update correctly";
    assertEquals(changed, retrieved, err);
    System.out.println("Successfully updated title");
  }

  // DELETE

  @Test
  public void testRemoveSection() {
    UUID sId = section1.getId();
    sectionService.createSection(section1);

    sectionService.removeSection(sId);
    boolean removed = sectionService.exists(sId);

    String err = "Error: section persists upon deletion";
    assertEquals(false, removed, err);
    System.out.println("Successfully deleted section");
  }

  @Test
  public void testRemovePage() {
    sectionService.removePage(section2.getId(), page2.getId());
    boolean exist = sectionService.getSectionById(section2.getId()).getPages().isEmpty();
    String err = "Error: page was not removed from the section properly";
    assertEquals(true, exist, err);
    System.out.println("Successfully removed a page from section");
  }
}
