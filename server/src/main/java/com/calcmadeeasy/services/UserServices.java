package com.calcmadeeasy.services;

import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.dto.Users.CreateUserDTO;
import com.calcmadeeasy.dto.Users.UserDTO;
import com.calcmadeeasy.dto.Users.UserProgressDTO;
import com.calcmadeeasy.dto.Users.UserResponseDTO;
import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.models.Users.UserCourseEnrollment;
import com.calcmadeeasy.models.Users.UserProgress;
import com.calcmadeeasy.repository.CourseEnrollmentRepo;
import com.calcmadeeasy.repository.UserRepo;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServices {
  private final UserRepo repo;
  private UserProgressService upService;
  private CourseServices courseService;
  private final CourseEnrollmentRepo enrollmentRepo;

  public UserServices(
      UserRepo repo,
      UserProgressService upService,
      CourseServices courseService,
      CourseEnrollmentRepo enrollmentRepo) {
    this.repo = repo;
    this.upService = upService;
    this.courseService = courseService;
    this.enrollmentRepo = enrollmentRepo;
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

  public boolean exists(UUID uId) {
    return repo.existsById(uId);
  }

  public UserDTO getUserDTO(UUID uId) {
    User u = repo.findById(uId)
        .orElseThrow(() -> new RuntimeException("User does not exist with id: " + uId));

    return new UserDTO(u);
  }

  public User getUser(UUID uId) {
    return repo.findById(uId).orElseThrow(() -> new RuntimeException("User does not exist with id: " + uId));
  }

  public List<UserDTO> getAllUsers() {
    List<User> users = repo.findAll();
    Map<UUID, List<UserProgressDTO>> progressMap = new HashMap<>();

    // Get all the user progresses and map them to progressMap using user's id as
    // the key.
    for (UserProgressDTO up : upService.getAllProgressDTO()) {
      progressMap
          .computeIfAbsent(up.getId(), k -> new ArrayList<>())
          .add(up);
    }

    // Stream the users and make new Data Transfer Objects for each one using the
    // user
    // itself and the corresponding UserProgress.
    return users.stream()
        .map(UserDTO::new)
        .toList();
  }

  // ==================== UPDATE ====================

  public UserDTO enrollCourse(UUID uId, UUID cId) {
    User u = getUser(uId);
    Course c = courseService.getCourseEntity(cId);
    UserCourseEnrollment uce = new UserCourseEnrollment(u, c);
    enrollmentRepo.save(uce);
    return new UserDTO(u);
  }

  public UserDTO unenrollCourse(UUID uId, UUID courseId) {
    User u = getUser(uId);
    u.unenrollCourse(courseId);

    Course course = courseService.getCourseEntity(courseId);

    // Getting all of the page ids to wipe user progress.
    List<UUID> pageIds = course.getChapters().stream()
        .flatMap((Chapter ch) -> ch.getSections().stream())
        .flatMap((Section s) -> s.getPages().stream())
        .map(Page::getId)
        .toList();

    List<UUID> progressToRemove = upService.getProgressForUserEntity(uId).stream()
        .filter(p -> pageIds.contains(p.getPage().getId()))
        .map(UserProgress::getId)
        .toList();

    upService.removeAll(progressToRemove);
    return new UserDTO(u);
  }

  // ==================== DELETE ====================

  public void removeUser(UUID uId) {
    repo.deleteById(uId);
  }

}
