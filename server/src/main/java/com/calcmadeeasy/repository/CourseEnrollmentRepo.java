package com.calcmadeeasy.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Users.UserCourseEnrollment;

@Repository
public interface CourseEnrollmentRepo extends JpaRepository<UserCourseEnrollment, UUID> {
  
  // Get course grade object by unique user id.
  Optional<UserCourseEnrollment> findByUserIdAndCourseId(UUID userId, UUID courseId);

  // Delete grade and join of a course and unique user for unenrolling from a course.
  void deleteByUserIdAndCourseId(UUID userId, UUID courseId);
  
}
