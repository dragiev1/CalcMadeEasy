package com.calcmadeeasy;

import com.calcmadeeasy.dto.Pages.CreatePageDTO;
import com.calcmadeeasy.dto.Pages.PageDTO;
import com.calcmadeeasy.dto.Pages.PageResponseDTO;
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
    private PageServices pageServices;

    private Page pageEntity;
    private PageDTO pageDTO;

    @BeforeEach
    public void setup() {
        pageEntity = Page.builder()
                .content("CONTENT")
                .build();
        PageResponseDTO response = pageServices.createPage(new CreatePageDTO(pageEntity));
        pageDTO = pageServices.getPageDTO(response.getId());
    }

    // Create

    @Test
    public void testPageCreation() {
        boolean exists = pageServices.exists(pageDTO.getId());

        assertTrue(exists);
    }

    // Retrevial

    @Test
    public void testGetAllPages() {
        List<PageDTO> pages = pageServices.getAllPages();

        assertEquals(1, pages.size());
    }

    @Test
    public void testGetPage() {

        Page found = pageServices.getPageEntity(pageDTO.getId());

        assertEquals(pageDTO.getId(), found.getId());
        assertEquals("CONTENT", found.getContent());
    }

    // Patching

    @Test
    public void testUpdateContent() {
        String ogContent = "CONTENT";
        pageEntity.setContent("REPLACED");
        CreatePageDTO dto = new CreatePageDTO(pageEntity);

        pageServices.updateContent(pageDTO.getId(), dto);

        Page found = pageServices.getPageEntity(pageDTO.getId());

        assertNotEquals(ogContent, found.getContent(), "Error: contents don't match");
    }

    // Delete

    @Test
    public void testDeletePage() {
        pageServices.deletePage(pageDTO.getId());
        boolean exists = pageServices.exists(pageDTO.getId());

        // Assert
        assertFalse(exists, "Error: page persisted after deletion");
    }

}