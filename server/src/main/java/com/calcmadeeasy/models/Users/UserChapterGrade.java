package com.calcmadeeasy.models.Users;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.calcmadeeasy.models.Chapters.Chapter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/*
 * Contains grade information about a unique chapter a user is studying.
 */

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = { "user_id", "chapter_id" })})
public class UserChapterGrade {
  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chapter_id", nullable = false)
  private Chapter chapter;

  private float grade;

  @CreationTimestamp
  @Column(nullable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  protected UserChapterGrade() {}

  public UserChapterGrade(User user, Chapter chapter, float grade) {
    this.user = user;
    this.chapter = chapter;
    this.grade = grade;
  }

  // Getters

  public UUID getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public Chapter getChapter() {
    return chapter;
  }

  public float getGrade() {
    return grade;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  // Setters

  public void setGrade(float grade) {
    this.grade = grade;
  }
  
}
