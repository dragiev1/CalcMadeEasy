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

import com.calcmadeeasy.dto.Sections.CreateSectionDTO;
import com.calcmadeeasy.dto.Sections.SectionDTO;
import com.calcmadeeasy.dto.Sections.SectionResponseDTO;
import com.calcmadeeasy.dto.Sections.UpdateSectionDTO;
import com.calcmadeeasy.services.SectionServices;

@RestController
@RequestMapping("/api/v1/sections")
public class SectionController {
  private SectionServices sectionService;

  public SectionController(SectionServices sectionServices) {
    this.sectionService = sectionServices;
  }

  // ---------------- CREATE ----------------

  @PostMapping
  public ResponseEntity<SectionResponseDTO> createSection(@RequestBody CreateSectionDTO section) {
    SectionResponseDTO s = sectionService.createSection(section);
    return ResponseEntity.ok(s);
  }

  // ---------------- READ ----------------

  @GetMapping("/{sectionId}")
  public ResponseEntity<SectionDTO> getSection(@PathVariable UUID sectionId) {
    SectionDTO s = sectionService.getSectionDTO(sectionId);
    return ResponseEntity.ok(s);
  }

  @GetMapping
  public ResponseEntity<List<SectionDTO>> getAllSections() {
    List<SectionDTO> ss = sectionService.getAllSections();
    return ResponseEntity.ok(ss);
  }

  // ---------------- UPDATE ----------------

  @PutMapping("/{sectionId}")
  public ResponseEntity<SectionDTO> updateSection(
      @PathVariable UUID sectionId,
      @RequestBody UpdateSectionDTO request) {
    SectionDTO s = sectionService.updateSection(sectionId, request);
    return ResponseEntity.ok(s);
  }

  @PutMapping("/{sectionId}/add-page/{pageId}")
  public ResponseEntity<SectionDTO> addPage(
      @PathVariable UUID sectionId,
      @PathVariable UUID pageId) {
    SectionDTO s = sectionService.addPage(sectionId, pageId);
    return ResponseEntity.ok(s);
  }

  // ---------------- DELETE ----------------

  @DeleteMapping("/{sectionId}")
  public ResponseEntity<Void> removeSection(@PathVariable UUID sectionId) {
    sectionService.removeSection(sectionId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{sectionId}/remove-page/{pageId}")
  public ResponseEntity<SectionDTO> removePage(
      @PathVariable UUID sectionId,
      @PathVariable UUID pageId) {
        SectionDTO s = sectionService.removePage(sectionId, pageId);
        return ResponseEntity.ok(s);
  }

}
