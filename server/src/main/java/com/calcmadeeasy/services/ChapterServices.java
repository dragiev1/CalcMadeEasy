package com.calcmadeeasy.services;

import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.repository.ChapterRepo;

import org.springframework.stereotype.Service;

@Service
public class ChapterServices {
  private final ChapterRepo repo;

  public ChapterServices(ChapterRepo repo) {
    this.repo = repo;
  }

  public Chapter createChapter(Chapter chapter) {
    return repo.save(chapter);
  }

  
}
