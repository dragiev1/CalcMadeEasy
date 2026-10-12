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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Contains information about a user's courses including their grades. 
 */

@Entity
@Table(uniqueConstraints = {
  @UniqueConstraint(columnNames = {"user_id", "course_id"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

  // Useful methods

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(!(o instanceof UserCourseEnrollment)) return false;
    UserCourseEnrollment that = (UserCourseEnrollment) o;
    return course.equals(that.course);
  }

  @Override
  public int hashCode() {
    return Objects.hash(course);
  } 

}
