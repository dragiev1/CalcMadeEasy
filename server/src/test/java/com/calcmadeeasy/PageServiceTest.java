package com.calcmadeeasy;

import com.calcmadeeasy.dto.Pages.CreatePageDTO;
import com.calcmadeeasy.dto.Pages.PageDTO;
import com.calcmadeeasy.dto.Pages.PageResponseDTO;
import com.calcmadeeasy.dto.Pages.UpdatePageDTO;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.services.PageServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test") // Using testing profile
public class PageServiceTest {

    @Autowired
    private PageServices pageService;

    private PageDTO pageDTO;

    @BeforeEach
    public void setup() {
        CreatePageDTO dto = new CreatePageDTO();
        dto.setContent("CONTENT");
        PageResponseDTO response = pageService.createPage(dto);
        pageDTO = pageService.getPageDTO(response.getId());
    }

    // Create

    @Test
    public void testPageCreation() {
        boolean exists = pageService.exists(pageDTO.getId());

        assertTrue(exists);
    }

    // Retrevial

    @Test
    public void testGetAllPages() {
        List<PageDTO> pages = pageService.getAllPages();

        assertEquals(1, pages.size());
    }

    @Test
    public void testGetPage() {

        Page found = pageService.getPageEntity(pageDTO.getId());

        assertEquals(pageDTO.getId(), found.getId());
        assertEquals("CONTENT", found.getContent());
    }

    // Patching

    @Test
    public void testUpdateContent() {
        String ogContent = "CONTENT";
        UpdatePageDTO update = new UpdatePageDTO();
        update.setContent("CHANGED");

        pageService.updatePage(pageDTO.getId(), update);

        Page found = pageService.getPageEntity(pageDTO.getId());

        assertNotEquals(ogContent, found.getContent(), "Error: contents don't match");
        assertEquals("CHANGED", found.getContent(), "Error: content was changed, but does not match expected");
    }

    // Delete

    @Test
    public void testDeletePage() {
        pageService.deletePage(pageDTO.getId());
        boolean exists = pageService.exists(pageDTO.getId());

        // Assert
        assertFalse(exists, "Error: page persisted after deletion");
    }

}