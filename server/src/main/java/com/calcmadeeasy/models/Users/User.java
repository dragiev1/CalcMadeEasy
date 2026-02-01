package com.calcmadeeasy.models.Users;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// TODO: Tie in with google auth 2.0 eventually to allow users to sign in with their google accounts.

@Entity
@Table(name = "app_user") // Because apparently user is not allowed as a table name.
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String profilePicUrl; 

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserCourseEnrollment> coursesEnrolled = new HashSet<>();

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  // No-argument constructor for JPA
  protected User() {
    this.coursesEnrolled = new HashSet<>();
  }

  // Inner class for building the User object
  public static class Builder {
    private String firstName;
    private String lastName;
    private String email;
    private String profilePicUrl;

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder profilePicUrl(String profilePicUrl) {
      this.profilePicUrl = profilePicUrl;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }

  private User(Builder b) {
    this.firstName = b.firstName;
    this.lastName = b.lastName;
    this.email = b.email;
    this.profilePicUrl = b.profilePicUrl;
  }

  // Getters
  public UUID getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getProfilePicUrl() {
    return profilePicUrl;
  }

  public int getNumCoursesTaking() {
    return coursesEnrolled.size();
  }

  public Set<UserCourseEnrollment> getCoursesEnrolled() {
    return coursesEnrolled;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  // Setters

  public void enrollNewCourse(UserCourseEnrollment newCourse) {
    this.coursesEnrolled.add(newCourse);
  }

  public void setProfilePicUrl(String newUrl) {
    this.profilePicUrl = newUrl;
  }

  public void unenrollCourse(UUID courseId) {
    if (coursesEnrolled == null || coursesEnrolled.isEmpty())
      throw new IllegalStateException("User is not enrolled in any courses");

    boolean removed = coursesEnrolled.removeIf(c -> c.getCourse().getId().equals(courseId));

    if (!removed)
      throw new IllegalArgumentException("No course was enrolled with id: " + courseId);
  }

  // Additional convenience methods.

  public boolean isEnrolledIn(UUID courseId) {
    return coursesEnrolled.stream()
        .anyMatch(c -> c.getCourse().getId().equals(courseId));
  }

  public String toString() {
    return "\nUser{\n" +
        "id=" + id +
        ", name=" + firstName + " " + lastName +
        ", email=" + email +
        ", profilePicUrl=" + profilePicUrl +
        ", numOfCourses=" + getNumCoursesTaking() +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "\n}";
  }

  public static Builder builder() {
    return new Builder();
  }
}
