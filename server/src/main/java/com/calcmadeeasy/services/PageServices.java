package com.calcmadeeasy.services;

import com.calcmadeeasy.repository.PageRepo;
import com.calcmadeeasy.dto.Pages.CreatePageDTO;
import com.calcmadeeasy.dto.Pages.PageDTO;
import com.calcmadeeasy.dto.Pages.PageProblemDTO;
import com.calcmadeeasy.dto.Pages.PageResponseDTO;
import com.calcmadeeasy.dto.Pages.UpdatePageDTO;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Problems.ProblemType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class PageServices {
  private final PageRepo repo;
  private ProblemServices problemService;

  public PageServices(PageRepo repo, ProblemServices problemService) {
    this.repo = repo;
    this.problemService = problemService;
  }

  // ==================== CREATE ====================

  public PageResponseDTO createPage(CreatePageDTO page) {
    Page p = Page.builder()
        .content(page.getContent())
        .build();
    
    repo.save(p);

    return new PageResponseDTO(p);
  }

  public List<Page> createPages(List<Page> pages) {
    return repo.saveAll(pages);
  }

  public List<Page> createPages(Page... pages) {
    return repo.saveAll(Arrays.asList(pages));
  }

  // ==================== READ ====================

  public List<Page> getAllPageEntities() {
    return repo.findAll();
  }

  public List<PageDTO> getAllPages() {
    return repo.findAll()
        .stream()
        .map(PageDTO::new)
        .toList();
  }

  public Page getPageEntity(UUID pageId) {
    return repo.findById(pageId).orElseThrow(() -> new RuntimeException("Page not found with id: " + pageId));
  }

  public PageDTO getPageDTO(UUID pageId) {
    Page page = repo.findById(pageId).orElseThrow(() -> new RuntimeException("Page not found with id: " + pageId));

    return new PageDTO(page);
  }

  public List<Page> getPagesBySection(UUID sectionId) {
    List<Page> pages = repo.findBySectionId(sectionId);
    if (pages.isEmpty())
      throw new RuntimeException("Section id " + sectionId + " either does not exist or has no pages");
    return pages;
  }

  public boolean exists(UUID pageId) {
    return repo.existsById(pageId);
  }

  // ==================== UPDATE ====================

  public PageResponseDTO updatePage(UUID pageId, UpdatePageDTO request) {
    Page page = getPageEntity(pageId);

    if (request.getContent() != null)
      page.setContent(request.getContent());

    repo.save(page);
    return new PageResponseDTO(page);
  }

  public PageDTO addProblem(PageProblemDTO dto) {
    Page page = getPageEntity(dto.getPageId());
    Problem problem = problemService.getProblemEntity(dto.getProblemId());
    ProblemType pType = dto.getProblemType();
    page.addProblem(problem, pType);

    repo.save(page);
    return new PageDTO(page);
  }

  // ==================== DELETE ====================

  // REMOVES the page from pages table.
  public void removePage(UUID pageId) {
    if (!repo.existsById(pageId)) {
      throw new RuntimeException("CANNOT REMOVE - page not found with id: " + pageId);
    }
    repo.deleteById(pageId);

  }

  public PageDTO removeProblemFromPage(UUID pageId, UUID problemId) {
    Page page = getPageEntity(pageId);
    page.removeProblem(problemId);

    repo.save(page);
    return new PageDTO(page);
  }

  public PageDTO removeAllProblems(UUID pageId) {
    Page page = getPageEntity(pageId);
    page.removeAllProblems();

    repo.save(page);
    return new PageDTO(page);
  }
}
