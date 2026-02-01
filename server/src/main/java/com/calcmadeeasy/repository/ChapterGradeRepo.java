package com.calcmadeeasy.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Users.UserChapterGrade;

@Repository
public interface ChapterGradeRepo extends JpaRepository<UserChapterGrade, UUID> {
  
  // Get specific chapter grade given a unique user id.
  Optional<UserChapterGrade> findByUserIdAndChapterId(UUID userId, UUID chapterId);

  // Get a list of all grades of a unique user of a specific course.
  List<UserChapterGrade> findByUserIdAndChapter_Course_Id(UUID userId, UUID courseId);

  // Delete all the chapter grades associated with a unique user on a specific course.
  void deleteByUserIdAndChapter_Course_Id(UUID userId, UUID chapterId);
  
}
