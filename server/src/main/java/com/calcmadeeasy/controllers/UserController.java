package com.calcmadeeasy.controllers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.calcmadeeasy.dto.Users.UserDTO;
import com.calcmadeeasy.dto.Users.UserProgressResponse;
import com.calcmadeeasy.dto.Users.UserProgressRequest;
import com.calcmadeeasy.dto.Users.CreateUserDTO;
import com.calcmadeeasy.dto.Users.UserResponseDTO;
import com.calcmadeeasy.services.UserProgressService;
import com.calcmadeeasy.services.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
  private final UserService userService;
  private final UserProgressService upService;

  // ---------------- CREATE ----------------
  
  @PostMapping
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserDTO newUser) {
    UserResponseDTO savedUser = userService.createUser(newUser);
    return ResponseEntity.ok(savedUser);
  }
  
  @PostMapping("/page/{pageId}/problems/{problemId}/verify")
  public ResponseEntity<UserProgressResponse> verifyUserSolution(
    @PathVariable UUID problemId,
    @AuthenticationPrincipal Jwt jwt,
    @PathVariable UUID pageId,
    @RequestBody UserProgressRequest request
  ) {
    UserProgressResponse response = upService.verifyAndRecordAttempt(pageId, problemId, jwt, request.getUserSolution());
    return ResponseEntity.ok(response);
  }

  // ---------------- READ ----------------

  @Target(ElementType.PARAMETER)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface CurrentUser {}

  @Component
  public class CurrentUserResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
      return parameter.getParameterAnnotation(CurrentUser.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parmeter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      return UUID.fromString(auth.getName());
    }
  }

  @Configuration
  public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private CurrentUserResolver currentUserResolver;
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
      resolvers.add(currentUserResolver);
    }
  }

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

  @GetMapping("/{userId}/{problemId}/{pageId}")
  public ResponseEntity<UserProgressResponse> getUserProgress(
    @CurrentUser UUID currentUserId,
    @PathVariable UUID problemId,
    @PathVariable UUID pageId
  ) {

    return upService.getProblemProgressForUserDTO(currentUserId, problemId, pageId)
          .map(dto -> ResponseEntity.ok(new UserProgressResponse(dto))).orElseGet(() -> ResponseEntity.ok(new UserProgressResponse()));
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
  public ResponseEntity<Void> removeUser(@PathVariable UUID userId) {
    userService.removeUser(userId);
    return ResponseEntity.ok().build();
  }

}
