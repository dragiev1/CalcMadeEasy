package com.calcmadeeasy.dto.Users;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* DTO used for verifying user's solution. */

// Inbound only.
@Getter
@Setter
@NoArgsConstructor
public class UserProgressRequest {
    private String userSolution;
}