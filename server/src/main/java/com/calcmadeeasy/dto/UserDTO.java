package com.calcmadeeasy.dto;

import java.util.UUID;

import com.calcmadeeasy.models.Users.User;

public class UserDTO {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String profilePicUrl;

  public UserDTO(User user) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.profilePicUrl = user.getProfilePicUrl();
  }

  // TODO: Add getters and setters.
}
