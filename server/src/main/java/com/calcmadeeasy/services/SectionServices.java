package com.calcmadeeasy.services;

import org.springframework.stereotype.Service;

import com.calcmadeeasy.repository.SectionRepo;

@Service
public class SectionServices {
  private final SectionRepo repo;

  public SectionServices(SectionRepo repo) {
    this.repo = repo;
  }

  // More complex methods here.

}
