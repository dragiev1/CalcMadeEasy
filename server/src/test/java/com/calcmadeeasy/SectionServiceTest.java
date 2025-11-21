package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.calcmadeeasy.dto.Pages.CreatePageDTO;
import com.calcmadeeasy.dto.Pages.PageDTO;
import com.calcmadeeasy.dto.Pages.PageResponseDTO;
import com.calcmadeeasy.dto.Sections.CreateSectionDTO;
import com.calcmadeeasy.dto.Sections.SectionDTO;
import com.calcmadeeasy.dto.Sections.SectionResponseDTO;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.services.PageServices;
import com.calcmadeeasy.services.SectionServices;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class SectionServiceTest {

  @Autowired
  private SectionServices sectionService;
  @Autowired
  private PageServices pageService;

  private Page page;
  private Section section;
  private PageDTO pageDTO;
  private SectionDTO sectionDTO;

  @BeforeEach
  public void setup() {
    page = Page.builder()
        .content("content (test)")
        .build();
    PageResponseDTO pageResponse = pageService.createPage(new CreatePageDTO(page));
    pageDTO = pageService.getPageDTO(pageResponse.getId());

    section = Section.builder()
        .description("description (test)")
        .title("title (test)")
        .pages(page)
        .build();

    SectionResponseDTO response = sectionService.createSection(new CreateSectionDTO(section));
    sectionDTO = sectionService.getSectionDTO(response.getId());
  }

  // CREATE

  @Test
  public void testCreateSection() {
    boolean exists = sectionService.exists(sectionDTO.getId());
    String err = "Section was not saved";
    assertTrue(exists, err);
  }

  // READ

  @Test
  public void testGetAllPages() {
    int size = sectionService.getAllSections().size();
    boolean retrieved1 = sectionService.exists(sectionDTO.getId());

    String err = "Error: section(s) was not saved properly ";
    assertEquals(1, size, err);
    assertEquals(true, retrieved1, err);
  }

  // UPDATE

  @Test
  public void testAddPage() {
    List<Page> ogPages = new ArrayList<>();
    ogPages.add(page);

    sectionService.addPage(sectionDTO.getId(), pageDTO.getId());
    int size = sectionService.getAllSections().size();

    String err = "Error: section was not saved properly";
    assertNotEquals(ogPages.size(), size);
    assertEquals(pageDTO.getId(), sectionService.getSectionEntity(sectionDTO.getId()).getPages().get(0).getId(), err);
  }

  // DELETE

  @Test
  public void testRemoveSection() {
    UUID id = sectionDTO.getId();

    sectionService.deleteSection(id);
    boolean removed = sectionService.exists(id);

    String err = "Error: section persists upon deletion";
    assertEquals(false, removed, err);
  }

  @Test
  public void testRemovePage() {
    sectionService.removePage(sectionDTO.getId(), pageDTO.getId());
    boolean exist = sectionService.getSectionEntity(sectionDTO.getId()).getPages().isEmpty();

    String err = "Error: page was not removed from the section properly";
    assertTrue(exist, err);
  }
}
