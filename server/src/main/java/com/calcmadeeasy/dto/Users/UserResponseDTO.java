package com.calcmadeeasy.dto.Users;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Users.User;

import lombok.Getter;

// Outbound only.
@Getter
public class UserResponseDTO {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String profilePicUrl;
  private Instant createdAt;

  public UserResponseDTO(User user) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.profilePicUrl = user.getProfilePicUrl();
    this.createdAt = user.getCreatedAt();
  }
}
