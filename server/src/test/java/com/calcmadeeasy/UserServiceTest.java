package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.services.CourseServices;
import com.calcmadeeasy.services.UserServices;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserServiceTest {
  @Autowired
  private UserServices userServices;
  @Autowired
  private CourseServices courseServices;

  private User user;
  private Course course;

  @BeforeEach
  public void setup() {
    
    course = Course.builder()
        .description("description1")
        .title("title1")
        .build();
    courseServices.createCourse(course);
    
    user = User.builder()
        .firstName("firstname")
        .lastName("lastname")
        .email("example@test.com")
        .profilePicUrl("/test")
        .build();
    userServices.createUser(user);
  }

  // Create

  @Test
  public void testCreateUser() {
    boolean exists = userServices.exists(user.getId());
    assertTrue(exists);
  }

  // Retrieval

  @Test
  public void testGetUser() {
    UUID id = user.getId();
    User u = userServices.getUser(id);
    boolean exists = userServices.exists(id);
    assertEquals(user, u, "Error: users do not match what was saved");
    assertTrue(exists, "Error: user does not exists within DB");
  }

  @Test
  public void testGetAllUsers() {
    int size = userServices.getAllUsers().size();

    String err = "Error: number of users did not match expected";
    assertEquals(1, size, err);
  }

  // Update

  @Test
  public void testEnrollNewCourse() {
    // Arrange
    int size = 1;
    UUID uId = user.getId();
    // Act
    userServices.addCourse(uId, course);
    int newSize = userServices.getUser(uId).getCourses().size();

    // Assert
    String err = "Error: course was not but was expected";
    assertEquals(size, newSize, "Error: number of courses did not match expected");
    assertTrue(userServices.getUser(uId).getCourses().contains(course), err);
  }

  // Remove

  @Test
  public void testUnenrollCourse() {
    userServices.addCourse(user.getId(), course);

    userServices.unenrollCourse(user.getId(), course.getId());

    boolean contains = userServices.getUser(user.getId()).getCourses().contains(course);
    int size = userServices.getUser(user.getId()).getCourses().size();

    assertFalse(contains, "Error: course persisted after supposed removal");
    assertEquals(0, size, "Error: courses is not empty");
  }
}
