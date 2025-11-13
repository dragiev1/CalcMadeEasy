package com.calcmadeeasy.dto;

import java.util.List;
import java.util.UUID;

import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.models.Users.UserProgress;

/*
 * Data Transfer Object for all/single User requests. 
 */
public class UserDTO {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String profilePicUrl;
  private List<UserProgress> up;

  public UserDTO(User user, List<UserProgress> up) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.profilePicUrl = user.getProfilePicUrl();
    this.up = up;
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

  public List<UserProgress> getUserProgress() {
    return up;
  }

  // Setters

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setProfilePicUrl(String profilePicUrl) {
    this.profilePicUrl = profilePicUrl;
  }

  public void setUserProgress(List<UserProgress> up) {
    this.up = up;
  }

}
