package com.calcmadeeasy.services;

import com.calcmadeeasy.repository.PageRepo;
import com.calcmadeeasy.models.Pages.Page;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;


@Service
public class PageServices {
  private final PageRepo repo;

  public PageServices(PageRepo repo) {
    this.repo = repo;
  }

  // Post
  public Page createPage(Page page) {
    return repo.save(page);
  }

  // Retrieval
  public List<Page> getAllPages() {
    return repo.findAll();
  }

  public Page getPageById(UUID pageId) {
    return repo.findById(pageId).orElseThrow(() -> new RuntimeException("Page not found!"));
  }

  public int getProblemCount(UUID pageId) {
  return repo.findProblemQuantity(pageId);
  }

  public List<Page> getPagesBySection(UUID sectionId) {
    return repo.findBySectionId(sectionId);
  }

  // Patching
  public void updateContent(UUID pageId, String newContent) {
    Page page = getPageById(pageId);
    page.setContent(newContent);
    repo.save(page);
  }

  // Removal
  public void deletePage(UUID pageId) {
    repo.deleteById(pageId);
  }

  public void deleteProblemFromPage(UUID pageId, UUID problemId) {
    Page page = getPageById(pageId);
    page.removeProblem(problemId);
    repo.save(page);
  }

}
