package com.calcmadeeasy.models.Users;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.calcmadeeasy.models.Sections.Section;

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
 * Entity for storing a section grade for a unique user. 
 */

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "section_id" }) })
public class UserSectionGrade {
  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "section_id", nullable = false)
  private Section section;

  private float grade;

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  protected UserSectionGrade() {}

  public UserSectionGrade(User user, Section section, float grade) {
    this.user = user;
    this.section = section;
    this.grade = grade;
  }

  // Getters

  public UUID getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public Section getSection() {
    return section;
  }

  public float getGrade() {
    return grade;
  }
  
  // No setters; immutable.
}
