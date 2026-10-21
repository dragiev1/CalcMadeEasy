package com.calcmadeeasy.services;

import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.dto.Users.CreateUserDTO;
import com.calcmadeeasy.dto.Users.UserDTO;
import com.calcmadeeasy.dto.Users.UserResponseDTO;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.models.Users.UserCourseEnrollment;
import com.calcmadeeasy.repository.UserProgressRepo;
import com.calcmadeeasy.repository.UserRepo;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
  private final UserRepo repo;
  private final UserProgressRepo upRepo;
  private CourseService courseService;
  private UserGradeService gradeServices;


  // ==================== CREATE ====================

  // Production method for user creation.
  @Transactional
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
  @Transactional
  public User createUserEntity(User user) {
    if (user == null)
      throw new IllegalArgumentException("Cannot save null user");
    return repo.save(user);
  }

  // ==================== READ ====================

  @Transactional(readOnly = true)
  public boolean exists(UUID userId) {
    return repo.existsById(userId);
  }

  @Transactional(readOnly = true)
  public UserDTO getUserDTO(UUID userId) {
    User u = repo.findById(userId)
        .orElseThrow(() -> new RuntimeException("User does not exist with id: " + userId));

    return new UserDTO(u);
  }

  @Transactional(readOnly = true)
  public User getUser(UUID userId) {
    return repo.findById(userId).orElseThrow(() -> new RuntimeException("User does not exist with id: " + userId));
  }

  @Transactional(readOnly = true)
  public List<UserDTO> getAllUsers() {
    List<User> users = repo.findAll();

    return users.stream()
        .map(UserDTO::new)
        .toList();
  }

  // ==================== UPDATE ====================

  @Transactional
  public UserDTO enrollCourse(UUID userId, UUID courseId) {
    User u = getUser(userId);
    Course c = courseService.getCourseEntity(courseId);
    UserCourseEnrollment uce = UserCourseEnrollment.builder().course(c).user(u).build();
    u.enrollNewCourse(uce);
    repo.save(u);
    return new UserDTO(u);
  }

  @Transactional
  public UserDTO unenrollCourse(UUID userId, UUID courseId) {
    User u = getUser(userId);

    if(!u.isEnrolledIn(courseId)) throw new IllegalArgumentException("User is not enrolled in this course"); 

    // Remove all the progress in course.
    upRepo.deleteByUserIdAndPage_Section_Chapter_Course_Id(userId, courseId);
    
    // Remove all grades calculated from progress.
    gradeServices.removeAllGrades(userId, courseId);
    
    // Unenroll user from course fully.
    u.unenrollCourse(courseId);
    
    return new UserDTO(u);
  }

  // ==================== DELETE ====================

  @Transactional
  public void removeUser(UUID userId) {
    repo.deleteById(userId);
    if(exists(userId)) throw new IllegalAccessError("User was not properly removed");
  }

}
