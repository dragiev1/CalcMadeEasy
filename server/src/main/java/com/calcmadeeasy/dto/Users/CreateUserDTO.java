package com.calcmadeeasy.dto.Users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Data Transfer Object for http responses regarding creating new Users.
 * NOTE: Will need to update this to tie in with Google OAuth 2.0. 
 * Inbound only.
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateUserDTO {
  @NotBlank(message = "First name cannot be blank.")
  @NotNull(message = "First name cannot be null.")
  private String firstName;
  @NotBlank(message = "Last name cannot be blank.")
  @NotNull(message = "Last name cannot be null.")
  private String lastName;
  @NotBlank(message = "Email cannot be blank.")
  @NotNull(message = "Email cannot be null.")
  private String email;
  private String profilePicUrl;
}
