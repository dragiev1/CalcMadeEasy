package models.Users;

import java.time.Instant;
import java.util.UUID;

import models.Problem.Problem;

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

}
