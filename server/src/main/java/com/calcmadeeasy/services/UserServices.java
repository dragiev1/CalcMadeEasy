package com.calcmadeeasy.services;

import com.calcmadeeasy.repository.UserRepo;

import org.springframework.stereotype.Service;

@Service
public class UserServices {
  private final UserRepo repo;

  public UserServices(UserRepo repo) {
    this.repo = repo;
  }

  // More complex methods here. i.e. getProgress
}
