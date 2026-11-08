package com.calcmadeeasy.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Users.UserProgress;

@Repository
public interface UserProgressRepo extends JpaRepository<UserProgress, UUID> {

  // Get all progress for a user
  List<UserProgress> findByUserId(UUID userId);

  // Get all progress for a page
  List<UserProgress> findByUserIdAndPageId(UUID userId, UUID pageId);

  // Get all progress for a problem
  Optional<UserProgress> findByUserIdAndProblemId(UUID userId, UUID problemId);
  
  // Retrieve progress with user, page, and problem id
  Optional<UserProgress> findByUserIdAndProblemIdAndPageId(UUID userId, UUID problemId, UUID pageId);

  // Get all progress for a user in a unique section
  List<UserProgress> findByUserIdAndPage_Section_Id(UUID userId, UUID sectionId);

  // For chapter-level computation
  List<UserProgress> findByUserIdAndPage_Section_Chapter_Id(UUID userId, UUID chapterId);

  // For course-level computation
  List<UserProgress> findByUserIdAndPage_Section_Chapter_Course_Id(UUID userId, UUID courseId);

  // Deletes all progress given a user and course id.
  void deleteByUserIdAndPage_Section_Chapter_Course_Id(UUID userId, UUID courseId);
  
}
