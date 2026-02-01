package com.calcmadeeasy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Users.UserCourseEnrollment;

@Repository
public interface CourseEnrollmentRepo extends JpaRepository<UserCourseEnrollment, UUID> {
  
}
