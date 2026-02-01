package com.calcmadeeasy.models.Users;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.calcmadeeasy.models.Courses.Course;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
  @UniqueConstraint(columnNames = {"user_id", "course_id"})
})
public class UserCourseEnrollment {
  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @CreationTimestamp
  @Column(updatable = false, nullable = false)
  private Instant enrolledAt;

  private float final_grade;

  protected UserCourseEnrollment() {}

  public UserCourseEnrollment(User user, Course course) {
    this.user = Objects.requireNonNull(user);
    this.course = Objects.requireNonNull(course);
  }

  // Getters

  public UUID getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public Course getCourse() {
    return course;
  }

  public float getGrade() {
    return final_grade;
  }

  // Setters

  public void setGrade(float newGrade) {
    this.final_grade = newGrade;
  }

  // Useful methods

  public boolean isEnrolledFor(User user, Course course) {
    return this.user.equals(user) && this.course.equals(course);
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(!(o instanceof UserCourseEnrollment)) return false;
    UserCourseEnrollment that = (UserCourseEnrollment) o;
    return user.equals(that.user) && course.equals(that.course);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, course);
  } 

}
