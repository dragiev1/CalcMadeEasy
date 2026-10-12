package com.calcmadeeasy.models.Users;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problems.Problem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * An object for storing progress with a many-to-many relationship including unique users and problems.  
 * Supports calculation of specific parts of a course's progress or total progress without bloating 
 * other classes.
*/
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "page_id", "problem_id" }))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "page_id", nullable = false)
  private Page page;

  private int attempts;
  private int pointsEarned;
  private boolean solved;
  private Instant lastAttempted;

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;


  // Setters
  public void recordAttempt(boolean isCorrect, Problem p) {
    if (solved)
      throw new IllegalArgumentException("Problem was already solved!");

    this.attempts++;
    this.lastAttempted = this.updatedAt;

    if (isCorrect && !solved) {
      this.solved = true;
      assignPoints(p.getPoints());
    }
  }

  public void assignPoints(int p) {
    this.pointsEarned = p;
  }

  public void resetProgress() {
    this.solved = false;
    this.attempts = 0;
    this.pointsEarned = 0;
    this.lastAttempted = null;
  }

}
