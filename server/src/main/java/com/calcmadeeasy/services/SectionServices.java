package com.calcmadeeasy.services;

import com.calcmadeeasy.models.Sections.Section;

import org.springframework.stereotype.Service;


@Service
public class SectionServices {
  private final Section section;

  public SectionServices(Section section) {
    this.section = section;
  }

  // More complex methods here.

}
