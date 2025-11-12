package com.calcmadeeasy.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.services.UserServices;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserServices userService;

  public UserController(UserServices userService) {
    this.userService = userService;
  }

  // ---------------- CREATE ----------------

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    User savedUser = userService.createUser(user);
    return ResponseEntity.ok(savedUser);
  }

  // ---------------- READ ----------------

  @GetMapping("/{id}")
  public ResponseEntity<User> getUser(@PathVariable UUID userId) {
    User user = userService.getUser(userId);
    return ResponseEntity.ok(user);
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  // ---------------- UPDATE ----------------

  @PutMapping("/{id}/enroll/{courseId}")
  public ResponseEntity<Void> enrollCourse(@PathVariable UUID userId, @PathVariable UUID courseId) {
    userService.enrollCourse(userId, courseId);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}/unenroll/{courseId}")
  public ResponseEntity<Void> unenrollCourse(@PathVariable UUID userId, @PathVariable UUID courseId) {
    userService.unenrollCourse(userId, courseId);
    return ResponseEntity.ok().build();
  }

  // ---------------- REMOVE ----------------

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
    userService.removeUser(userId);
    return ResponseEntity.ok().build();
  }

}
