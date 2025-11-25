package com.calcmadeeasy.dto.Users;


/*
 * Data Transfer Object for http responses regarding creating new Users.
 * NOTE: Will need to update this to tie in with Google OAuth 2.0. 
 * Inbound only.
 */
public class CreateUserDTO {
  private String firstName;
  private String lastName;
  private String email;
  private String profilePicUrl;

  // No args constructor for Jackson.
  public CreateUserDTO() {}


  // Getters

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

}
