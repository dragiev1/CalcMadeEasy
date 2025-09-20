package com.calcmadeeasy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Courses.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, UUID> {
  
}
