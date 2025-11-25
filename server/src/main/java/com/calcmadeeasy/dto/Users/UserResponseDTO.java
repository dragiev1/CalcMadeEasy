package com.calcmadeeasy.dto.Users;

import java.util.UUID;

import com.calcmadeeasy.models.Users.User;

// Outbound only.
public class UserResponseDTO {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String profilePicUrl;

  // No args constructor for Jackson.
  public UserResponseDTO() {
  }

  public UserResponseDTO(User user) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.profilePicUrl = user.getProfilePicUrl();
  }

  // Getters

  public UUID getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getProfilePicUrl() {
    return profilePicUrl;
  }

}
