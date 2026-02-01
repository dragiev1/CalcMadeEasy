package com.calcmadeeasy.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Users.UserSectionGrade;

@Repository
public interface SectionGradeRepo extends JpaRepository<UserSectionGrade, UUID> {

  // Gets the specfic section grades for a unique user.
  Optional<UserSectionGrade> findByUserIdAndSectionId(UUID userId, UUID sectionId);
  
  // Gets section grades for a specfic course and user.
  List<UserSectionGrade> findByUserIdAndSection_Chapter_CourseId(UUID userId, UUID courseId);

  // Deletes section grades for a specific course and user.
  void deleteByUserIdAndSection_Chapter_CourseId(UUID userId, UUID courseId);
}
