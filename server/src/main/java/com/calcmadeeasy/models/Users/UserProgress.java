package com.calcmadeeasy.models.Users;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Problem.Problem;

/*
 * An object for storing progress with a many-to-many relationship including unique users and problems.  
 * Supports for calculation of specific parts of a course's progress or total progress without bloating 
 * other classes.
*/
public class UserProgress {
  private final UUID id;
  private final UUID userId;
  private final UUID problemId;

  private int attempts;
  private int pointsEarned;
  private boolean solved;

  private Instant lastAttempted;
  private Instant createdAt;
  private Instant updatedAt;

  public UserProgress(UUID userId, UUID problemId) {
    this.id = UUID.randomUUID();
    this.userId = userId;
    this.problemId = problemId;
    this.attempts = 0;
    this.solved = false;
    this.pointsEarned = 0;
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }

  public void touch() {
    this.updatedAt = Instant.now();
  }

  // Getters
  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public UUID getProblemId() {
    return problemId;
  }

  public int getAttempts() {
    return attempts;
  }

  public boolean isSolved() {
    return solved;
  }

  public int getPointsEarned() {
    return pointsEarned;
  }

  public Instant getLastAttemptedAt() {
    return lastAttempted;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  // Setters
  public void recordAttempt(boolean isCorrect, Problem p) {
    this.attempts++;
    touch();
    this.lastAttempted = this.updatedAt;

    if (isCorrect && !solved) {
      this.solved = true;
      this.pointsEarned = p.getPoints();
    }
  }

  public String toString() {
    return "\nUserProgress{\n" +
        "id=" + id +
        ", UserId=" + userId +
        ", ProblemId=" + problemId +
        ", pointsGiven=" + pointsEarned +
        ", solved=" + solved +
        ", attempts=" + attempts +
        ", lastAttemptedAt=" + lastAttempted +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "\n}";
  }
}
