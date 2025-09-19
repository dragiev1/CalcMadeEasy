package com.calcmadeeasy.services;

import com.calcmadeeasy.models.Users.User;

import org.springframework.stereotype.Service;


@Service
public class UserServices {
  private final User user;

  public UserServices(User user) {
    this.user = user;
  }

  // More complex methods here. i.e. getProgress

}
