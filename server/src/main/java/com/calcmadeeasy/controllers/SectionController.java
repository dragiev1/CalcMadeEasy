package com.calcmadeeasy.controllers;

import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calcmadeeasy.dto.Sections.CreateSectionDTO;
import com.calcmadeeasy.dto.Sections.SectionResponseDTO;
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
    SectionResponseDTO p = sectionService.createSection(section);
    return ResponseEntity.ok(p);
  }


  // ---------------- READ ----------------

  @GetMapping("/{sectionId}")
  public ResponseEntity<SectionDTO> getSection(@PathVariable UUID sectionId) {

  }


}
