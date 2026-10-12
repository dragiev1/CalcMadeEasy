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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Tie in with google auth 2.0 eventually to allow users to sign in with their google accounts.

@Entity
@Table(name = "app_user") // Because apparently user is not allowed as a table name.
@Data // Generates getters, setters, toString, equals, hashCode methods
@Builder  // Generates the builder pattern automatically
@NoArgsConstructor  // Required for JPA/Hibernate
@AllArgsConstructor  // Required for @Builder to work properly
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String firstName;
  private String lastName;
  private String email;
  private String profilePicUrl; 

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private Set<UserCourseEnrollment> coursesEnrolled = new HashSet<>();

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  // Getters

  public int getNumCoursesTaking() {
    return coursesEnrolled.size();
  }

  // Setters

  public void enrollNewCourse(UserCourseEnrollment newCourse) {
    this.coursesEnrolled.add(newCourse);
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

}
