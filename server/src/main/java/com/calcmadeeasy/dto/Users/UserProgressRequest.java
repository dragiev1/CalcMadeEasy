package com.calcmadeeasy.dto.Users;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* DTO used for verifying user's solution. */

// Inbound only.
@Getter
@Setter
@NoArgsConstructor
public class UserProgressRequest {
    @NotBlank(message = "User's submission cannot be blank.")
    private String userSolution;
}