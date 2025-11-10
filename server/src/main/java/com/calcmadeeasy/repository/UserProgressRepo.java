package com.calcmadeeasy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Users.UserProgress;

@Repository
public interface UserProgressRepo extends JpaRepository<UserProgress, UUID> {
  
  List<UserProgress> findByUserId(UUID userId);

  List<UserProgress> findByPageId(UUID userId);

  List<UserProgress> findByProblemId(UUID userId);
}
