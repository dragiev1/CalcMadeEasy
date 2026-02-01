package com.calcmadeeasy.services;

import org.springframework.stereotype.Service;

import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.repository.ChapterGradeRepo;
import com.calcmadeeasy.repository.CourseEnrollmentRepo;
import com.calcmadeeasy.repository.SectionGradeRepo;
import com.calcmadeeasy.repository.UserProgressRepo;

import jakarta.transaction.Transactional;

/*
 * Service for handling grade aggregation, caching, and retrieval.
 */

@Service
@Transactional
public class UserGradeServices {
  private UserProgressRepo upRepo;
  private ChapterGradeRepo chapterGradeRepo;
  private SectionGradeRepo sectionGradeRepo;
  private CourseEnrollmentRepo courseEnrollmentRepo;

  public UserGradeServices(
      UserProgressRepo upRepo,
      ChapterGradeRepo chapterGradeRepo,
      SectionGradeRepo sectionGradeRepo,
      CourseEnrollmentRepo courseEnrollmentRepo) {
    this.upRepo = upRepo;
    this.chapterGradeRepo = chapterGradeRepo;
    this.sectionGradeRepo = sectionGradeRepo;
    this.courseEnrollmentRepo = courseEnrollmentRepo;
  }

  // ==================== UPDATE ====================

  public void updateGrades(User user, Page page) {

  }

  

}
