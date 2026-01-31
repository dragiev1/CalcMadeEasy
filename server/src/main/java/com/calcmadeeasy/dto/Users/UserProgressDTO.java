package com.calcmadeeasy.dto.Users;

import java.time.Instant;
import java.util.UUID;

import com.calcmadeeasy.models.Users.UserProgress;

// Outbound only.
public class UserProgressDTO {
  private UUID id;
  private UUID userId;
  private UUID problemId;
  private UUID pageId;

  // Problem fields.
  private Boolean isChallenge;
  private Integer points;

  private Integer attempts;
  private Integer pointsEarned;
  private Boolean solved;
  private Instant lastAttempted;

  private Instant createdAt;
  private Instant updatedAt;

  public UserProgressDTO() {}

  public UserProgressDTO(UserProgress up) {
    this.id = up.getId();
    this.userId = up.getUser().getId();
    this.pageId = up.getPage().getId();
    this.problemId = up.getProblem().getId();
    this.points = up.getProblem().getPoints();
    this.isChallenge = up.getProblem().getIsChallenge();
    this.attempts = up.getAttempts();
    this.pointsEarned = up.getPointsEarned();
    this.solved = up.isSolved();
    this.createdAt = up.getCreatedAt();
    this.updatedAt = up.getUpdatedAt();
  }

  // Getters

  public UUID getId() {
    return id;
  }

  public UUID getUser() {
    return userId;
  }

  public UUID getProblem() {
    return problemId;
  }

  public Boolean getIsChallenge() {
    return isChallenge;
  }

  public Integer getPoints() {
    return points;
  }

  public UUID getPage() {
    return pageId;
  }

  public Integer getAttempts() {
    return attempts;
  }

  public Boolean isSolved() {
    return solved;
  }

  public Integer getPointsEarned() {
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

  // No setters; immutable.
}
