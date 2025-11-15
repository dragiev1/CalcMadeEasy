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

import com.calcmadeeasy.dto.Users.CreateUserDTO;
import com.calcmadeeasy.dto.Users.UserDTO;
import com.calcmadeeasy.dto.Users.UserResponseDTO;

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
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserDTO newUser) {
    UserResponseDTO savedUser = userService.createUser(newUser);
    return ResponseEntity.ok(savedUser);
  }

  // ---------------- READ ----------------

  @GetMapping("/{userId}")
  public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId) {
    UserDTO user = userService.getUserDTO(userId);
    return ResponseEntity.ok(user);
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<UserDTO> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  // ---------------- UPDATE ----------------

  @PutMapping("/{userId}/enroll/{courseId}")
  public ResponseEntity<Void> enrollCourse(@PathVariable UUID userId, @PathVariable UUID courseId) {
    userService.enrollCourse(userId, courseId);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{userId}/unenroll/{courseId}")
  public ResponseEntity<Void> unenrollCourse(@PathVariable UUID userId, @PathVariable UUID courseId) {
    userService.unenrollCourse(userId, courseId);
    return ResponseEntity.ok().build();
  }

  // ---------------- DELETE ----------------

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
    userService.removeUser(userId);
    return ResponseEntity.ok().build();
  }

}
