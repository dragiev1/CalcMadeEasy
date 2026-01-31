package com.calcmadeeasy.dto.Users;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.calcmadeeasy.models.Users.User;

/*
 * Data Transfer Object for all/single User requests. 
 * Outbound only. 
 */
public class UserDTO {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String profilePicUrl;
  private List<UserProgressDTO> up;
  private Instant updatedAt;
  private Instant createdAt;

  // No args constructor for Jackson.
  public UserDTO() {}

  public UserDTO(User user, List<UserProgressDTO> up) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.profilePicUrl = user.getProfilePicUrl();
    this.up = up;
    this.updatedAt = user.getUpdatedAt();
    this.createdAt = user.getCreatedAt();
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

  public List<UserProgressDTO> getUserProgress() {
    return up;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

}
