package com.calcmadeeasy.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Users.UserChapterGrade;

@Repository
public interface ChapterGradeRepo extends JpaRepository<UserChapterGrade, UUID> {
  
  // Get grade for a chapter given user id.
  Optional<UserChapterGrade> findByUserIdAndChapterId(UUID userId, UUID chapterId);
}
