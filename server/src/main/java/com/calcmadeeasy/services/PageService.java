package com.calcmadeeasy.services;

import com.calcmadeeasy.repository.PageRepo;

import lombok.AllArgsConstructor;

import com.calcmadeeasy.dto.Pages.CreatePageDTO;
import com.calcmadeeasy.dto.Pages.PageDTO;
import com.calcmadeeasy.dto.Pages.PageProblemDTO;
import com.calcmadeeasy.dto.Pages.PageResponseDTO;
import com.calcmadeeasy.dto.Pages.UpdatePageDTO;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problems.Problem;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PageService {
  private final PageRepo repo;
  // private final PageProblemRepo pageProblemRepo;  // Eventually will need it probably. 
  private ProblemService problemService;

  // ==================== CREATE ====================

  @Transactional
  public PageResponseDTO createPage(CreatePageDTO page) {
    Page p = Page.builder()
        .content(page.getContent())
        .position(page.getPosition())
        .build();

    repo.save(p);

    return new PageResponseDTO(p);
  }

  @Transactional
  public List<Page> createPages(List<Page> pages) {
    return repo.saveAll(pages);
  }

  @Transactional
  public List<Page> createPages(Page... pages) {
    return repo.saveAll(Arrays.asList(pages));
  }

  // ==================== READ ====================

  @Transactional(readOnly = true)
  public List<Page> getAllPageEntities() {
    return repo.findAll();
  }

  @Transactional(readOnly = true)
  public List<PageDTO> getAllPages() {
    return repo.findAll()
        .stream()
        .map(PageDTO::new)
        .toList();
  }

  @Transactional(readOnly = true)
  public Page getPageEntity(UUID pageId) {
    return repo.findById(pageId).orElseThrow(() -> new RuntimeException("Page not found with id: " + pageId));
  }

  @Transactional(readOnly = true)
  public PageDTO getPageDTO(UUID pageId) {
    Page page = repo.findById(pageId).orElseThrow(() -> new RuntimeException("Page not found with id: " + pageId));

    return new PageDTO(page);
  }

  @Transactional(readOnly = true)
  public List<Page> getPagesBySection(UUID sectionId) {
    List<Page> pages = repo.findBySectionId(sectionId);
    if (pages.isEmpty())
      throw new RuntimeException("Section id " + sectionId + " either does not exist or has no pages");
    return pages;
  }

  @Transactional(readOnly = true)
  public boolean exists(UUID pageId) {
    return repo.existsById(pageId);
  }

  // ==================== UPDATE ====================

  @Transactional
  public PageResponseDTO updatePage(UUID pageId, UpdatePageDTO request) {
    Page page = getPageEntity(pageId);

    if (request.getContent() != null)
      page.setContent(request.getContent());
    if (request.getPosition() != null)
      page.setPosition(request.getPosition());

    repo.save(page);
    return new PageResponseDTO(page);
  }

  @Transactional
  public PageDTO addProblem(UUID pageId, UUID problemId, PageProblemDTO dto) {
    Page page = getPageEntity(pageId);
    Problem problem = problemService.getProblemEntity(problemId);
    page.addProblem(problem, dto.getProblemType());

    repo.save(page);
    return new PageDTO(page);
  }

  // ==================== DELETE ====================

  // REMOVES the page from pages table.
  @Transactional
  public void removePage(UUID pageId) {
    if (!repo.existsById(pageId)) {
      throw new RuntimeException("CANNOT REMOVE - page not found with id: " + pageId);
    }
    repo.deleteById(pageId);

  }

  @Transactional
  public PageDTO removeProblemFromPage(UUID pageId, UUID problemId) {
    Page page = getPageEntity(pageId);
    page.removeProblem(problemId);

    repo.save(page);
    return new PageDTO(page);
  }

  @Transactional
  public PageDTO removeAllProblems(UUID pageId) {
    Page page = getPageEntity(pageId);
    page.removeAllProblems();

    repo.save(page);
    return new PageDTO(page);
  }
}
