package com.calcmadeeasy.dto.Users;

import java.time.Instant;

import com.calcmadeeasy.models.Users.UserProgress;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/* DTO used for sending user's problem results */

// Outbound only.
@Getter
@AllArgsConstructor
public class UserProgressResponse {
    @NotNull(message = "Must provide a boolean value.")
    private boolean isCorrect;
    private int attempts;
    private Instant lastAttempted;
    private int pointsEarned;
    private boolean maxAttemptsReached;

    public UserProgressResponse(UserProgressDTO dto) {
        this.isCorrect = dto.getSolved(); 
        this.attempts = dto.getAttempts();
        this.lastAttempted = dto.getLastAttempted();
        this.pointsEarned = dto.getPointsEarned();
        this.maxAttemptsReached = dto.getAttempts() >= 3;
    }

    public UserProgressResponse(UserProgress up) {
        this.isCorrect = up.isSolved(); 
        this.attempts = up.getAttempts();
        this.lastAttempted = up.getLastAttempted();
        this.pointsEarned = up.getPointsEarned();
        this.maxAttemptsReached = up.getAttempts() >= 3;
    }

    // Convenience Constructor for sending empty progress. 
    public UserProgressResponse() {
        this.isCorrect = false;
        this.attempts = 0;
        this.lastAttempted = null;
        this.pointsEarned = 0;
        this.maxAttemptsReached = false;
    }
}
