package com.calcmadeeasy.services;

import com.calcmadeeasy.repository.ChapterRepo;

import org.springframework.stereotype.Service;


@Service
public class ChapterServices {
  private final ChapterRepo repo;

  public ChapterServices(ChapterRepo repo) {
    this.repo = repo;
  }

  // More complex methods here.

}
