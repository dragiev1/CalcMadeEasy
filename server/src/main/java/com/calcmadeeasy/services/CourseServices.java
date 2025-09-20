package com.calcmadeeasy.services;

import com.calcmadeeasy.repository.CourseRepo;

import org.springframework.stereotype.Service;


@Service
public class CourseServices {
  private final CourseRepo repo;

  public CourseServices(CourseRepo repo) {
    this.repo = repo;
  }

  // More complex methods here.

}
