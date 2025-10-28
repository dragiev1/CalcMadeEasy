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

    // Create

    @Test
    public void testPageCreation() {
        // Arrange
        Page page = Page.builder()
                .content("Test page creation")
                .build();

        pageServices.createPage(page);

        // Act
        boolean exists = pageServices.exists(page.getId());

        // Assert
        assertEquals(exists, true);

        System.out.println("Successfully created page: " + page);
    }

    // Retrevial

    @Test
    public void testGetAllPages() {

        // Arrange
        Page page = Page.builder()
                .content("Test content (retrevial)")
                .build();

        pageServices.createPage(page);

        // Act
        List<Page> pages = pageServices.getAllPages();

        // Assert
        assertEquals(1, pages.size());
        System.out.println("Found " + pages.size() + " pages");
        // pages.forEach(page -> System.out.println(page)); // Debugging.
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

    // TODO: Move this test to a SectionServiceTest.java file
    // public void testGetPagesBySection() {

    // // Arrange
    // UUID sectionId = UUID.randomUUID();

    // Page page1 = Page.builder()
    // .content("Test content 1 (getPagesBySection)")
    // .build();

    // Page page2 = Page.builder()
    // .content("Test content 2 (getPagesBySection)")
    // .build();

    // pageServices.createPages(page1, page2);

    // // Act
    // List<Page> pages = pageServices.getAllPages();

    // // Assert
    // assertEquals(pages, Arrays.asList(page1, page2));
    // System.out.println("");
    // }

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
        assertEquals(saved.getContent(), found.getContent());
        System.out.println("Successfully changed content, " + ogContent + "\t--->\t" + found.getContent());

    }

    // Delete

    @Test
    public void testDeletePage() {

        // Arrange
        Page page = Page.builder()
                .content("Test content (deletePage)")
                .build();
        pageServices.createPage(page);

        // Act
        pageServices.deletePage(page.getId());
        boolean exists = pageServices.exists(page.getId());

        // Assert
        assertEquals(exists, false);
        System.out.println("Successfully deleted page: " + page);
    }

}