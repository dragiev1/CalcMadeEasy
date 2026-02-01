package com.calcmadeeasy.services;

import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.dto.Users.CreateUserDTO;
import com.calcmadeeasy.dto.Users.UserDTO;
import com.calcmadeeasy.dto.Users.UserResponseDTO;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.models.Users.UserCourseEnrollment;
import com.calcmadeeasy.repository.UserRepo;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServices {
  private final UserRepo repo;
  private UserProgressService upService;
  private CourseServices courseService;
  private UserGradeServices gradeServices;

  public UserServices(
      UserRepo repo,
      UserProgressService upService,
      CourseServices courseService,
      UserGradeServices gradeServices) {
    this.repo = repo;
    this.upService = upService;
    this.courseService = courseService;
    this.gradeServices = gradeServices;
  }

  // ==================== CREATE ====================

  // Production method for user creation.
  public UserResponseDTO createUser(CreateUserDTO user) {
    if (user == null)
      throw new IllegalArgumentException("Cannot save null user");

    User u = User.builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .profilePicUrl(user.getProfilePicUrl())
        .build();

    repo.save(u);

    return new UserResponseDTO(u);
  }

  // Internal only method for user creation.
  public User createUserEntity(User user) {
    if (user == null)
      throw new IllegalArgumentException("Cannot save null user");
    return repo.save(user);
  }

  // ==================== READ ====================

  public boolean exists(UUID userId) {
    return repo.existsById(userId);
  }

  public UserDTO getUserDTO(UUID userId) {
    User u = repo.findById(userId)
        .orElseThrow(() -> new RuntimeException("User does not exist with id: " + userId));

    return new UserDTO(u);
  }

  public User getUser(UUID userId) {
    return repo.findById(userId).orElseThrow(() -> new RuntimeException("User does not exist with id: " + userId));
  }

  public List<UserDTO> getAllUsers() {
    List<User> users = repo.findAll();

    return users.stream()
        .map(UserDTO::new)
        .toList();
  }

  // ==================== UPDATE ====================

  public UserDTO enrollCourse(UUID userId, UUID courseId) {
    User u = getUser(userId);
    Course c = courseService.getCourseEntity(courseId);
    UserCourseEnrollment uce = new UserCourseEnrollment(u, c);
    u.enrollNewCourse(uce);
    repo.save(u);
    return new UserDTO(u);
  }

  public UserDTO unenrollCourse(UUID userId, UUID courseId) {
    User u = getUser(userId);

    if(!u.isEnrolledIn(courseId)) throw new IllegalArgumentException("User is not enrolled in this course"); 

    // Remove all the progress in course.
    upService.removeAllProgressByUserAndCourseId(userId, courseId);
    
    // Remove all grades calculated from progress.
    gradeServices.removeAllGrades(userId, courseId);
    
    // Unenroll user from course fully.
    u.unenrollCourse(courseId);
    
    return new UserDTO(u);
  }

  // ==================== DELETE ====================

  public void removeUser(UUID userId) {
    repo.deleteById(userId);
    if(exists(userId)) throw new IllegalAccessError("User was not properly removed");
  }

}
