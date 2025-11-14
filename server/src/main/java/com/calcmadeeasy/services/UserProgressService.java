package com.calcmadeeasy.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.calcmadeeasy.dto.Users.UserProgressDTO;
import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Users.UserProgress;
import com.calcmadeeasy.repository.UserProgressRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserProgressService {
  private UserProgressRepo repo;

  public UserProgressService(UserProgressRepo repo) {
    this.repo = repo;
  }

  // ==================== CREATE ====================

  public UserProgress createUserProgress(UserProgress up) {
    return repo.save(up);
  }

  // ==================== RETRIEVE ====================

  public UserProgressDTO getUserProgress(UUID upId) {

    return new UserProgressDTO(repo.findById(upId)
        .orElseThrow(() -> new RuntimeException("UserProgress could not be found with id: " + upId)));
  }

  public UserProgress getUserProgressEntity(UUID upId) {
    return repo.findById(upId)
        .orElseThrow(() -> new RuntimeException("UserProgress does not exists with id: " + upId));
  }

  public List<UserProgressDTO> getProgressForUserDTO(UUID userId) {
    return getProgressForUserEntity(userId)
        .stream()
        .map(UserProgressDTO::new)
        .toList();
  }

  public List<UserProgress> getProgressForUserEntity(UUID userId) {
    if (userId == null)
      throw new IllegalArgumentException("");

    return repo.findByUserId(userId);
  }

  public List<UserProgressDTO> getAllProgressDTO() {
    return getAllProgresses()
        .stream()
        .map(UserProgressDTO::new)
        .toList();
  }

  private List<UserProgress> getAllProgresses() {
    return repo.findAll();
  }

  // TODO: add a get grade for a section here.

  public boolean exists(UUID upId) {
    return repo.existsById(upId);
  }

  // ==================== UPDATE ====================

  public void recordAttempt(UUID upId, boolean isCorrect, Problem p) {
    UserProgress up = getUserProgressEntity(upId);
    up.recordAttempt(isCorrect, p);
  }

  /*
   * Resets user progress for one specfic problem on one page.
   * Does not delete the object from DB
   */
  public void resetProgress(UUID upId) {
    if (!exists(upId))
      throw new IllegalArgumentException("Section does not exist, cannot remove");

    UserProgress up = getUserProgressEntity(upId);
    up.resetProgress();

    if (up.isSolved() || up.getAttempts() != 0)
      throw new RuntimeException("User progress did not reset correctly!");
  }

  // ==================== REMOVE ====================

  public void removeProgress(UUID upId) {
    if (!exists(upId))
      throw new IllegalArgumentException("UserProgress does not exist with id," + upId + ", cannot remove");

    repo.deleteById(upId);
    boolean removed = exists(upId);

    if (!removed)
      throw new IllegalArgumentException("UserProgress persists despite being removed");
  }

  public void removeAll(List<UUID> ups) {
    repo.deleteAllById(ups);
  }
}
