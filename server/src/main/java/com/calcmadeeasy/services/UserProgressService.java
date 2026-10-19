package com.calcmadeeasy.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calcmadeeasy.dto.Users.UserProgressDTO;
import com.calcmadeeasy.dto.Users.UserProgressResponse;
import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.models.Users.UserProgress;
import com.calcmadeeasy.repository.UserProgressRepo;


/*
 * Service for handling raw progress updates. 
 */

@Service
public class UserProgressService {
  private UserProgressRepo repo;

  private ProblemService problemService;
  private UserService userService;

  public UserProgressService(UserProgressRepo repo) {
    this.repo = repo;
  }

  // ==================== CREATE ====================

  @Transactional
  public UserProgress createUserProgress(UserProgress up) {
    return repo.save(up);
  }

  // ==================== READ ====================

  @Transactional(readOnly = true)
  public UserProgressDTO getUserProgressDTO(UUID upId) {

    return new UserProgressDTO(repo.findById(upId)
        .orElseThrow(() -> new RuntimeException("UserProgress could not be found with id: " + upId)));
  }

  @Transactional(readOnly = true)
  public UserProgress getUserProgressEntity(UUID upId) {
    return repo.findById(upId)
        .orElseThrow(() -> new RuntimeException("UserProgress does not exists with id: " + upId));
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
      throw new IllegalArgumentException("");

    return repo.findByUserId(userId);
  }

  @Transactional(readOnly = true)
  public UserProgress getProblemProgressForUser(User user, Problem problem) {
    return repo.findByProblemIdAndUserId(user.getId(), problem.getId())
      .orElseGet(() -> {
        UserProgress newProgress = new UserProgress();
        newProgress.setUser(user);
        newProgress.setProblem(problem);
        newProgress.setAttempts(0);
        newProgress.setPointsEarned(0);
        return createUserProgress(newProgress);
      });
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
  public UserProgressResponse verifyAndRecordAttempt(UUID problemId, UUID userId, String userSolution) {
    // Fetch problem entity using problem service. 
    Problem p = problemService.getProblemEntity(problemId);
    User u = userService.getUser(userId);

    // Get solution results.
    boolean isCorrect = problemService.verifySolution(p, userSolution);

    // Record result and then save.
    UserProgress up = getProblemProgressForUser(u, p);
    up.recordAttempt(isCorrect, p);
    repo.save(up);

    // Return the response for client.
    return new UserProgressResponse(
      isCorrect,
      up.getAttempts(),
      up.getLastAttempted(),
      up.getPointsEarned()
    );
  }

  /*
   * Resets user progress for one specfic problem on one page.
   * Does not remove the object from DB
   */
  @Transactional
  public void resetProgress(UUID upId) {
    if (!exists(upId))
      throw new IllegalArgumentException("UserProgress does not exist, cannot reset");

    UserProgress up = getUserProgressEntity(upId);
    up.resetProgress();

    if (up.isSolved() || up.getAttempts() != 0)
      throw new RuntimeException("User progress did not reset correctly!");
  }

  // ==================== DELETE ====================

  @Transactional
  public void removeProgress(UUID upId) {
    if (!exists(upId))
      throw new IllegalArgumentException("UserProgress does not exist with id," + upId + ", cannot remove");

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
