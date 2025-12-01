package com.calcmadeeasy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calcmadeeasy.dto.Chapters.ChapterResponseDTO;
import com.calcmadeeasy.dto.Chapters.CreateChapterDTO;
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
    return chapterService.createChapter(chapter);
  }
}
