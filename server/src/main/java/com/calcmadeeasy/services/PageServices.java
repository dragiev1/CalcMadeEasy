package com.calcmadeeasy.services;

import com.calcmadeeasy.repository.PageRepo;
import com.calcmadeeasy.models.Pages.Page;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class PageServices {
  private final PageRepo repo;

  public PageServices(PageRepo repo) {
    this.repo = repo;
  }

  // ==================== CREATE ====================

  public Page createPage(Page page) {
    return repo.save(page);
  }

  public List<Page> createPages(List<Page> pages) {
    return repo.saveAll(pages);
  }

  public List<Page> createPages(Page... pages) {
    return repo.saveAll(Arrays.asList(pages));
  }

  // ==================== READ ====================

  public List<Page> getAllPages() {
    return repo.findAll();
  }

  public Page getPageById(UUID pageId) {
    return repo.findById(pageId).orElseThrow(() -> new RuntimeException("Page not found with id: " + pageId));
  }

  public int getProblemCount(UUID pageId) {
    return repo.findById(pageId)
        .map(Page::getProblemQuantity)
        .orElseThrow(() -> new RuntimeException("Page not found with id: " + pageId));
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

  public void updateContent(UUID pageId, String newContent) {
    Page page = getPageById(pageId);
    page.setContent(newContent);
    repo.save(page);
  }

  

  // ==================== DELETE ====================

  // DELETES the page from pages table.
  public void deletePage(UUID pageId) {
    if (!repo.existsById(pageId)) {
      throw new RuntimeException("Cannot delete - page not found with id: " + pageId);
    }
    repo.deleteById(pageId);

  }

  /*
   * REMOVES ALL PAGES IN SECTION!
   * Does NOT delete the pages from the pages table.
   */
  public void removeAllPagesInSection(UUID sectionId) {
    List<Page> pages = repo.findBySectionId(sectionId);
    repo.deleteAll(pages);
  }

}
