package com.calcmadeeasy.services;

import com.calcmadeeasy.models.Pages.Page;

import org.springframework.stereotype.Service;

@Service
public class PageServices {
  private final Page page;

  public PageServices(Page page) {
    this.page = page;
  }

  // More complex methods here.
}
