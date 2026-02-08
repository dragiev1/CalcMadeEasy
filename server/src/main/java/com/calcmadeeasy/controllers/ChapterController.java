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

import com.calcmadeeasy.dto.Chapters.ChapterDTO;
import com.calcmadeeasy.dto.Chapters.ChapterResponseDTO;
import com.calcmadeeasy.dto.Chapters.CreateChapterDTO;
import com.calcmadeeasy.dto.Chapters.UpdateChapterDTO;
import com.calcmadeeasy.services.ChapterServices;

@RestController
@RequestMapping("/api/v1/chapters")
public class ChapterController {
  private ChapterServices chapterService;

  public ChapterController(ChapterServices chapterService) {
    this.chapterService = chapterService;
  }
  
  // ---------------- CREATE ----------------

  @PostMapping
  public ResponseEntity<ChapterResponseDTO> createChapter(@RequestBody CreateChapterDTO chapter) {
    ChapterResponseDTO c = chapterService.createChapter(chapter);
    return ResponseEntity.ok(c);
  }

  // ---------------- RETRIEVE ----------------

  @GetMapping("/{chapterId}")
  public ResponseEntity<ChapterDTO> getChapter(@PathVariable UUID chapterId) {
    ChapterDTO c = chapterService.getChapterDTO(chapterId);
    return ResponseEntity.ok(c);
  }

  @GetMapping
  public ResponseEntity<List<ChapterDTO>> getAllChapters() {
    List<ChapterDTO> cl = chapterService.getAllChapters();
    return ResponseEntity.ok(cl);
  }

  // ---------------- UPDATE ----------------
  
  @PutMapping("/{chapterId}")
  public ResponseEntity<ChapterResponseDTO> updateChapter(
    @PathVariable UUID chapterId, 
    @RequestBody UpdateChapterDTO request) {
      ChapterResponseDTO c = chapterService.updateChapter(chapterId, request);
      return ResponseEntity.ok(c);
  } 

  @PutMapping("/move-section-to/{chapterId}/{sectionId}")
  public ResponseEntity<ChapterDTO> addSection(
    @PathVariable UUID chapterId,
    @PathVariable UUID sectionId
  ) {
    ChapterDTO c = chapterService.moveSection(chapterId, sectionId);
    return ResponseEntity.ok(c);
  }

  // ---------------- REMOVE ----------------

  @DeleteMapping("/{chapterId}/remove-section/{sectionId}")
  public ResponseEntity<ChapterDTO> removeSection(
    @PathVariable UUID chapterId, 
    @PathVariable UUID sectionId) {
    ChapterDTO c = chapterService.removeSection(chapterId, sectionId);
    return ResponseEntity.ok(c);
  } 

  @DeleteMapping("/{chapterId}")
  public ResponseEntity<Void> removeChapter(@PathVariable UUID chapterId) {
    chapterService.removeChapter(chapterId);
    return ResponseEntity.ok().build();
  }

}
