package com.calcmadeeasy.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.models.Users.UserChapterGrade;
import com.calcmadeeasy.models.Users.UserCourseEnrollment;
import com.calcmadeeasy.models.Users.UserProgress;
import com.calcmadeeasy.models.Users.UserSectionGrade;
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
  private final UserProgressRepo upRepo;
  private final ChapterGradeRepo chapterGradeRepo;
  private final SectionGradeRepo sectionGradeRepo;
  private final CourseEnrollmentRepo courseEnrollmentRepo;

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

  // Called when new problem is solved.
  public void updateGrades(User user, Page page) {
    Section section = page.getSection();
    Chapter chapter = section.getChapter();
    Course course = chapter.getCourse();

    // Section grade logic.
    float sectionGrade = computeSectionGrade(user, section);
    UserSectionGrade sg = sectionGradeRepo.findByUserIdAndSectionId(user.getId(), section.getId())
        .orElse(new UserSectionGrade(user, section, sectionGrade));
    sg.setGrade(sectionGrade);
    sectionGradeRepo.save(sg);

    // Chapter grade logic.
    float chapterGrade = computeChapterGrade(user, chapter);
    UserChapterGrade cg = chapterGradeRepo.findByUserIdAndChapterId(user.getId(), chapter.getId())
        .orElse(new UserChapterGrade(user, chapter, chapterGrade));
    cg.setGrade(chapterGrade);
    chapterGradeRepo.save(cg);

    // Course grade logic.
    float courseGrade = computeCourseGrade(user, course);
    UserCourseEnrollment uce = courseEnrollmentRepo.findByUserIdAndCourseId(user.getId(), course.getId())
      .orElseThrow(() -> new IllegalArgumentException("User is not enrolled in a course"));
    uce.setGrade(courseGrade);
    courseEnrollmentRepo.save(uce);

  }

  public float computeSectionGrade(User user, Section section) {
    List<UserProgress> progresses = upRepo.findByUserIdAndPage_SectionId(user.getId(), section.getId());
    return calculateGrade(progresses);
  }

  public float computeChapterGrade(User user, Chapter chapter) {
    List<UserProgress> progresses = upRepo.findByUserIdAndPage_Section_ChapterId(user.getId(), chapter.getId());
    return calculateGrade(progresses);
  }

  public float computeCourseGrade(User user, Course course) {
    List<UserProgress> progresses = upRepo.findByUserIdAndPage_Section_Chapter_CourseId(user.getId(), course.getId());
    return calculateGrade(progresses);
  }

  public float calculateGrade(List<UserProgress> progresses) {
    if (progresses == null || progresses.isEmpty())
      return 0f;

    float totalPoints = 0f;
    float earnedPoints = 0f;

    for (UserProgress up : progresses) {
      totalPoints += up.getProblem().getPoints();
      earnedPoints += up.getPointsEarned();
    }

    return totalPoints == 0f ? 0f : (earnedPoints / totalPoints) * 100f;
  }

}
