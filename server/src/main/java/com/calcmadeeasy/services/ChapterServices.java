package com.calcmadeeasy.services;

import com.calcmadeeasy.models.Chapters.Chapter;

import org.springframework.stereotype.Service;


@Service
public class ChapterServices {
  private final Chapter chapter;

  public ChapterServices(Chapter chapter) {
    this.chapter = chapter;
  }

  // More complex methods here.

}
