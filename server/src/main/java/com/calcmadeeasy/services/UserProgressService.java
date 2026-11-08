package com.calcmadeeasy.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calcmadeeasy.dto.Users.UserProgressDTO;
import com.calcmadeeasy.dto.Users.UserProgressResponse;
import com.calcmadeeasy.exceptions.UserProgressNotFoundException;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.models.Users.UserProgress;
import com.calcmadeeasy.repository.UserProgressRepo;

import lombok.AllArgsConstructor;

/*
 * Service for handling raw progress updates. 
 */

@Service
@AllArgsConstructor
public class UserProgressService {
  private final UserProgressRepo repo;

  private final UserService userService;
  private final ProblemService problemService;
  private final PageService pageService;

  // ==================== CREATE ====================

  @Transactional
  public UserProgress createUserProgress(UserProgress up) {
    return repo.save(up);
  }

  // ==================== READ ====================

  @Transactional(readOnly = true)
  public UserProgressDTO getUserProgressDTO(UUID upId) {

    return new UserProgressDTO(repo.findById(upId)
        .orElseThrow(() -> new UserProgressNotFoundException("UserProgress could not be found with id: " + upId)));
  }

  @Transactional(readOnly = true)
  public UserProgress getUserProgressEntity(UUID upId) {
    return repo.findById(upId)
        .orElseThrow(() -> new UserProgressNotFoundException("UserProgress does not exists with id: " + upId));
  }

  @Transactional(readOnly = true)
  public List<UserProgressDTO> getProgressForUserDTO(UUID userId) {
    return getProgressForUserEntity(userId)
        .stream()
        .map(UserProgressDTO::new)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<UserProgress> getProgressForUserEntity(UUID userId) {
    if (userId == null)
      throw new IllegalArgumentException("User ID cannot be null");

    return repo.findByUserId(userId);
  }

  @Transactional(readOnly = true)
  public Optional<UserProgress> getProblemProgressForUser(UUID userId, UUID pageId, UUID problemId) {
    return repo.findByUserIdAndProblemIdAndPageId(userId, problemId, pageId);
  }

  @Transactional(readOnly = true)
  public List<UserProgressDTO> getAllProgressDTO() {
    return getAllProgresses()
        .stream()
        .map(UserProgressDTO::new)
        .toList();
  }

  @Transactional(readOnly = true)
  private List<UserProgress> getAllProgresses() {
    return repo.findAll();
  }

  @Transactional(readOnly = true)
  public boolean exists(UUID upId) {
    return repo.existsById(upId);
  }

  // ==================== UPDATE ====================

  @Transactional
  public UserProgressResponse verifyAndRecordAttempt(UUID pageId, UUID problemId, Jwt jwt, String userSolution) {
    // Extract the user id from the jwt
    UUID userId = UUID.fromString(jwt.getSubject());

    // Fetch problem entity using problem service.
    Page pg = pageService.getPageEntity(pageId);
    Problem p = problemService.getProblemEntity(problemId);
    User u = userService.getUser(userId);

    // Try to find existing progress for this specific page.
    Optional<UserProgress> existingProgress = repo.findByUserIdAndProblemIdAndPageId(userId, problemId, pageId);

    // Get solution results.
    boolean isCorrect = problemService.verifySolution(p, userSolution);

    if (existingProgress.isPresent()) {
      UserProgress up = existingProgress.get();
      up.setAttempts(up.getAttempts() + 1);
      up.setLastAttempted(Instant.now());

      if (!up.isSolved() && isCorrect) {
        up.setSolved(true);
        up.setPointsEarned(p.getPoints());
      }

      return new UserProgressResponse(isCorrect, up.getAttempts(), up.getLastAttempted(), up.getPointsEarned());
    } else {
      int pointsToAward = isCorrect ? p.getPoints() : 0;
      // Create new user progress if progress does not exist.
      UserProgress newProgress = UserProgress.builder()
          .user(u)
          .problem(p)
          .page(pg)
          .attempts(1)
          .solved(isCorrect)
          .pointsEarned(pointsToAward)
          .lastAttempted(Instant.now())
          .build();

      repo.save(newProgress);
      return new UserProgressResponse(isCorrect, newProgress.getAttempts(), newProgress.getLastAttempted(),
          newProgress.getPointsEarned());
    }
  }

  /*
   * Resets user progress for one specfic problem on one page.
   * Does not remove the object from DB
   */
  @Transactional
  public void resetProgress(UUID upId) {
    UserProgress up = getUserProgressEntity(upId);
    up.resetProgress();
  }

  // ==================== DELETE ====================

  @Transactional
  public void removeProgress(UUID upId) {
    if (!exists(upId))
      throw new UserProgressNotFoundException("UserProgress does not exist with id," + upId + "; cannot remove");

    repo.deleteById(upId);
  }

  @Transactional
  public void removeAllProgressByUserAndCourseId(UUID userId, UUID courseId) {
    repo.deleteByUserIdAndPage_Section_Chapter_Course_Id(userId, courseId);
  }

  @Transactional
  public void removeAll(List<UUID> ups) {
    repo.deleteAllById(ups);

    for (UUID ids : ups)
      if (exists(ids))
        throw new IllegalArgumentException("All user progress was not removed correctly");
  }
}
