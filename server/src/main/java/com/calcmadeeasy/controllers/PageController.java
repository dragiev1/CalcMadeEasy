package com.calcmadeeasy.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calcmadeeasy.dto.Pages.CreatePageDTO;
import com.calcmadeeasy.dto.Pages.PageDTO;
import com.calcmadeeasy.dto.Pages.PageProblemDTO;
import com.calcmadeeasy.dto.Pages.PageResponseDTO;
import com.calcmadeeasy.services.PageServices;

@RestController
@RequestMapping("/api/v1/pages")
public class PageController {
  private PageServices pageService;

  public PageController(PageServices pageServices) {
    this.pageService = pageServices;
  }

  // ---------------- CREATE ----------------

  @PostMapping
  public ResponseEntity<PageResponseDTO> createPage(@RequestBody CreatePageDTO page) {
    PageResponseDTO p = pageService.createPage(page);
    return ResponseEntity.ok(p);
  }

  // ---------------- READ ----------------

  @GetMapping("/{pageId}")
  public ResponseEntity<PageDTO> getPage(@PathVariable UUID pageId) {
    PageDTO p = pageService.getPageDTO(pageId);
    return ResponseEntity.ok(p);
  }

  @GetMapping
  public ResponseEntity<List<PageDTO>> getAllPages() {
    List<PageDTO> pages = pageService.getAllPages();
    return ResponseEntity.ok(pages);
  }

  // ---------------- UPDATE ----------------

  @PutMapping("/{pageId}")
  public ResponseEntity<PageDTO> updatePage(
    @PathVariable UUID pageId, 
    @RequestBody CreatePageDTO request) {
      PageDTO p = pageService.updateContent(pageId, request);
      return ResponseEntity.ok(p);
  }

  @PutMapping("/add-problem")
  public ResponseEntity<PageDTO> addProblem(
    @RequestBody PageProblemDTO pageProblemDTO) {
      PageDTO p = pageService.addProblem(pageProblemDTO);
      return ResponseEntity.ok(p);
    }

  // ---------------- DELETE ----------------
  
  @DeleteMapping("/{pageId}")
  public ResponseEntity<Void> deletePage(@PathVariable UUID pageId) {
    pageService.deletePage(pageId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{pageId}/problems/{problemId}")
  public ResponseEntity<PageDTO> deleteProblemFromPage(
    @PathVariable UUID pageId,
    @PathVariable UUID problemId
  ) {
    PageDTO p = pageService.deleteProblemFromPage(pageId, problemId);
    return ResponseEntity.ok(p);
  }

  @DeleteMapping("/{pageId}/problems/remove-all")
  public ResponseEntity<PageDTO> deleteAllProblems(@PathVariable UUID pageId) {
    PageDTO p = pageService.deleteAllProblems(pageId);
    return ResponseEntity.ok(p);
  }
}
