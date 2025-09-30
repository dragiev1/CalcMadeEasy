package com.calcmadeeasy;

import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problem.Problem;
import com.calcmadeeasy.models.Problem.ProblemSolutionType;
import com.calcmadeeasy.models.Problem.ProblemType;
import com.calcmadeeasy.repository.PageRepo;
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
@ActiveProfiles("test")  // Using test profile
public class PageServiceTest {

    @Autowired
    private PageRepo pageRepo;

    @Autowired
    private PageServices pageServices;

    @Test
    public void testRetrieveAllPages() {

        // Create some problems
        Problem p1 = Problem.builder()
                .description("test problem")
                .solutionType(ProblemSolutionType.EXPRESSION)
                .solution("test problem solution")
                .points(10)
                .isChallenge(false)
                .build();
        // Arrange: Create test data
        Page page1 = Page.builder()
                .content("Test content")
                .build();

        page1.setProblem(p1, ProblemType.EXERCISE);
        
        pageRepo.save(page1);

        // Act
        List<Page> pages = pageServices.getAllPages();

        // Assert
        assertNotNull(pages);
        assertEquals(1, pages.size());
        System.out.println("Found " + pages.size() + " pages");
        pages.forEach(page -> System.out.println("Page(s): " + page));
    }
}