package com.calcmadeeasy.services;

import com.calcmadeeasy.repository.PageRepo;

import org.springframework.stereotype.Service;

@Service
public class PageServices {
  private final PageRepo repo;

  public PageServices(PageRepo repo) {
    this.repo = repo;
  }

  // More complex methods here.
}
