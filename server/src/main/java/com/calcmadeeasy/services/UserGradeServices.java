package com.calcmadeeasy.services;

import org.springframework.stereotype.Service;

import com.calcmadeeasy.repository.UserProgressRepo;

import jakarta.transaction.Transactional;

/*
 * Service for handling grade aggregation, caching, and retrieval.
 */

@Service
@Transactional
public class UserGradeServices {
  private UserProgressRepo upRepo;

  public UserGradeServices(UserProgressRepo upRepo) {
    this.upRepo = upRepo;
  }

  // ==================== CREATE ====================

  
}
