package com.calcmadeeasy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Users.UserProgress;

@Repository
public interface UserProgressRepo extends JpaRepository<UserProgress, UUID> {

  // Get all progress for a user
  List<UserProgress> findByUserId(UUID userId);

  // Get all progress for a page
  List<UserProgress> findByPageId(UUID userId);

  // Get all progress for a problem
  List<UserProgress> findByProblemId(UUID userId);

  // Get all progress for a user in a unique section
  List<UserProgress> findByUserIdAndPage_SectionId(UUID userId, UUID sectionId);

  // For chapter-level computation
  List<UserProgress> findByUserIdAndPage_Section_ChapterId(UUID userId, UUID chapterId);

  // For course-level computation
  List<UserProgress> findByUserIdAndPage_Section_Chapter_CourseId(UUID userId, UUID courseId);

  // Deletes all progress given a user and course id.
  void deleteByUserIdAndPage_Section_Chapter_CourseId(UUID userId, UUID courseId);
  
}
