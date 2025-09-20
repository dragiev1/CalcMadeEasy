package com.calcmadeeasy.models.Users;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.calcmadeeasy.models.Courses.Course;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity 
@Table(name = "app_user")  // Because apparently user is not allowed as a table name.
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String profilePicUrl; // Will store url provided by Google
  private int numCourseTaking;
  
  @OneToMany(cascade = CascadeType.ALL)
  private List<Course> courses; // Courses the user is enrolled in

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  // No-argument constructor for JPA
  public User() {
    this.courses = new ArrayList<>();
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }

  // Inner class for building the User object
  public static class Builder {
    private String firstName;
    private String lastName;
    private String email;
    private String profilePicUrl;
    private int numCourseTaking;
    private List<Course> courses = new ArrayList<>();
    private Instant createdAt;
    private Instant updatedAt;

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

    public Builder numCoursesTaking(int numCoursesTaking) {
      this.numCourseTaking = numCoursesTaking;
      return this;
    }

    public Builder courses(Course... courses) {
      if(courses == null || courses.length == 0)
      this.courses = List.of(courses);
      return this;
    }

    public Builder createdAt(Instant createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder updatedAt(Instant updatedAt) {
      this.updatedAt = updatedAt;
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
    this.numCourseTaking = b.numCourseTaking;
    this.courses = b.courses == null ? new ArrayList<>() : new ArrayList<>(b.courses);
    this.createdAt = b.createdAt;
    this.updatedAt = b.updatedAt;
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
    return numCourseTaking;
  }

  public List<Course> getCourses() {
    return courses;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  // Setters
  public void touch() {
    this.updatedAt = Instant.now();
  }

  public void setCourseQuantity(int newQuantity) {
    this.numCourseTaking = newQuantity;
  }

  public void enrollNewCourse(Course newCourse) {
    if (courses == null)
      courses = new ArrayList<>();
    else
      setCourseQuantity(getNumCoursesTaking() + 1);

    this.courses.add(newCourse);
    touch();
  }

  public void setProfilePicUrl(String newUrl) {
    this.profilePicUrl = newUrl;
    touch();
  }

  public String toString() {
    return "\nUser{\n" +
        "id=" + id +
        ", name=" + firstName + " " + lastName +
        ", email=" + email +
        ", profilePicUrl=" + profilePicUrl +
        ", numOfCourses=" + numCourseTaking +
        // ", courses=" + courses.toString() +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "\n}";
  }

  public static Builder builder() {
    return new Builder();
  }
}
