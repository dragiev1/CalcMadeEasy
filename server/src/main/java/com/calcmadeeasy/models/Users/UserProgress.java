package com.calcmadeeasy.models.Users;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.calcmadeeasy.models.Problem.Problem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/*
 * An object for storing progress with a many-to-many relationship including unique users and problems.  
 * Supports for calculation of specific parts of a course's progress or total progress without bloating 
 * other classes.
*/
@Entity
public class UserProgress {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "problem_id", nullable = false)
  private Problem problem;

  private int attempts;
  private int pointsEarned;
  private boolean solved;
  private Instant lastAttempted;

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  // No-args constructor for JPA
  public UserProgress() {
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }

  public UserProgress(User user, Problem problem) {
    this.user = user;
    this.problem = problem;
    this.attempts = 0;
    this.solved = false;
    this.pointsEarned = 0;
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }

  public void touch() {
    this.updatedAt = Instant.now();
  }

  public User getUser() {
    return user;
  }

  public Problem getProblemId() {
    return problem;
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
        ", UserId=" + user.toString() +
        ", ProblemId=" + problem.toString() +
        ", pointsGiven=" + pointsEarned +
        ", solved=" + solved +
        ", attempts=" + attempts +
        ", lastAttemptedAt=" + lastAttempted +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "\n}";
  }
}
