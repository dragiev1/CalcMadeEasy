package models.Users;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.Courses.Course;

public class User {
  private final UUID id; // Unique identifier for user
  private String firstName;
  private String lastName;
  private String email;
  private String profilePicUrl; // Will store url provided by Google
  private int numCourseTaking;
  private List<Course> courses; // Stores courses the user is enrolled in
  private Instant createdAt;
  private Instant updatedAt;

  // Inner class for building the User object
  public static class Builder {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePicUrl;
    private int numCourseTaking;
    private List<Course> courses = new ArrayList<>();
    private Instant createdAt;
    private Instant updatedAt;

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

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
    this.id = b.id == null ? UUID.randomUUID() : b.id;
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
