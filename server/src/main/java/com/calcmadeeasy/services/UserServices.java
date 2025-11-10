package com.calcmadeeasy.services;

import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.repository.UserRepo;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServices {
  private final UserRepo repo;

  public UserServices(UserRepo repo) {
    this.repo = repo;
  }

  // ==================== CREATE ====================

  public User createUser(User user) {
    if (user == null)
      throw new IllegalArgumentException("Cannot save null user");
    return repo.save(user);
  }

  public List<User> createUsers(User... users) {
    if (users == null || users.length == 0)
      throw new IllegalArgumentException("Cannot save null user");
    return repo.saveAll(List.of(users));
  }

  public List<User> createUsers(List<User> users) {
    if (users == null || users.isEmpty())
      throw new IllegalArgumentException("Cannot save null user");
    return repo.saveAll(users);
  }

  // ==================== RETRIEVAL ====================

  public boolean exists(UUID uId) {
    return repo.existsById(uId);
  }

  public User getUser(UUID uId) {
    return repo.findById(uId).orElseThrow(() -> new RuntimeException("User does not exist with id: " + uId));
  }

  public List<User> getAllUsers() {
    return repo.findAll();
  }

  // ==================== UPDATE ====================

  public void addCourse(UUID uId, Course c) {
    User u = getUser(uId);
    u.enrollNewCourse(c);
  }

  // ==================== REMOVE ====================

  public void removeUser(UUID uId) {
    repo.deleteById(uId);
  }

  public void unenrollCourse(UUID uId, UUID courseId) {
    User u = getUser(uId);
    u.unenrollCourse(courseId);
  }

}
