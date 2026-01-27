package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  private PageDTO pageDTO;
  private SectionDTO sectionDTO;

  @BeforeEach
  public void setup() {

    CreatePageDTO pdto = new CreatePageDTO();
    pdto.setContent("CONTENT");
    PageResponseDTO pageResponse = pageService.createPage(pdto);
    pageDTO = pageService.getPageDTO(pageResponse.getId());


    CreateSectionDTO cdto = new CreateSectionDTO();
    cdto.setDescription("DESCRIPTION");
    cdto.setTitle("TITLE");
    SectionResponseDTO response = sectionService.createSection(cdto);
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
    assertTrue(retrieved1, err);
  }

  // UPDATE

  @Test
  public void testAddPage() {
    SectionDTO updated = sectionService.addPage(sectionDTO.getId(), pageDTO.getId());
    int size = updated.getPages().size();

    String err = "Error: section was not saved properly";
    assertEquals(1, size, err);
    assertEquals(1, updated.getPages().get(0).getPosition());
    assertEquals(pageDTO.getId(), updated.getPages().get(0).getId(), err);
  }

  // DELETE

  @Test
  public void testRemoveSection() {
    UUID id = sectionDTO.getId();

    sectionService.deleteSection(id);
    boolean removed = sectionService.exists(id);

    String err = "Error: section persists upon deletion";
    assertFalse(removed, err);
  }

  @Test
  public void testRemovePage() {
    sectionService.addPage(sectionDTO.getId(), pageDTO.getId());

    sectionService.removePage(sectionDTO.getId(), pageDTO.getId());
    boolean exist = sectionService.getSectionEntity(sectionDTO.getId()).getPages().isEmpty();

    String err = "Error: page was not removed from the section properly";
    assertTrue(exist, err);
  }
}
