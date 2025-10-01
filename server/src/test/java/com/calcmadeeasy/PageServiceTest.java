package com.calcmadeeasy;

import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problem.Problem;
import com.calcmadeeasy.models.Problem.ProblemSolutionType;
import com.calcmadeeasy.models.Problem.ProblemType;
import com.calcmadeeasy.services.PageServices;
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

    // Retrevial

    @Test
    public void testRetrieveAllPages() {

        // Arrange
        Page page1 = Page.builder()
                .content("Test content (retrevial)")
                .build();

        pageServices.createPage(page1);

        // Act
        List<Page> pages = pageServices.getAllPages();

        // Assert
        assertNotNull(pages);
        assertEquals(1, pages.size());
        System.out.println("Found " + pages.size() + " pages");
        // pages.forEach(page -> System.out.println(page));  // Debugging.
    }

    @Test
    public void testGetPageById() {

        // Arrange
        Page page = Page.builder()
                .content("Test content (getPageById)")
                .build();
        Page saved = pageServices.createPage(page);

        // Act
        Page found = pageServices.getPageById(saved.getId());

        // Assert
        assertNotNull(found);
        assertEquals(saved, found);
        assertEquals("Test content (getPageById)", found.getContent());
        System.out.println("Found page ID:\t" + found.getId());

    }

    @Test
    public void testGetProblemCount() {

        // Arrange
        Problem p1 = Problem.builder()
                .description("test problem")
                .solutionType(ProblemSolutionType.EXPRESSION)
                .solution("test problem solution")
                .points(10)
                .isChallenge(false)
                .build();

        Page page = Page.builder()
                .content("Test content (retrevial)")
                .build();

        page.setProblem(p1, ProblemType.EXERCISE);

        Page saved = pageServices.createPage(page);

        // Act
        int found = pageServices.getProblemCount(page.getId());

        // Assert
        assertEquals(found, saved.getProblemQuantity());
        System.out.println("Found " + found + " problems in page");
    }

    // Patching

    @Test
    public void testUpdateContent() {
        // Arrange
        String ogContent = "Test content (retrevial)";
        Page page = Page.builder()
                .content(ogContent)
                .build();

        Page saved = pageServices.createPage(page);
        pageServices.updateContent(saved.getId(), "Replaced!");

        // Act
        Page found = pageServices.getPageById(page.getId());

        // Assert
        assertNotNull(found);
        assertEquals(saved.getContent(), found.getContent());
        System.out.println("Successfully changed content, " + ogContent + "\t--->\t" + found.getContent());

    }

}