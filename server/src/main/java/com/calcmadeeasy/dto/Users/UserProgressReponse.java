package com.calcmadeeasy.dto.Users;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/* DTO used for sending user's problem results */

// Outbound only.
@Getter
public class UserProgressReponse {
    @NotNull(message = "Must provide a boolean value.")
    private boolean isCorrect;
    private int attempts;
    private Instant lastAttempted;
    private int pointsEarned;
}