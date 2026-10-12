package com.calcmadeeasy.services;

import java.util.List;
import java.util.UUID;

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
public class UserGradeService {
  private final UserProgressRepo upRepo;
  private final ChapterGradeRepo chapterGradeRepo;
  private final SectionGradeRepo sectionGradeRepo;
  private final CourseEnrollmentRepo courseEnrollmentRepo;

  public UserGradeService(
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
    float sectionGrade = computeSectionGrade(user.getId(), section.getId());
    UserSectionGrade sg = sectionGradeRepo.findByUserIdAndSectionId(user.getId(), section.getId())
        .orElse(UserSectionGrade.builder().user(user).section(section).grade(sectionGrade).build());
    sg.setGrade(sectionGrade);
    sectionGradeRepo.save(sg);


    // Chapter grade logic.
    float chapterGrade = computeChapterGrade(user.getId(), chapter.getId());
    UserChapterGrade cg = chapterGradeRepo.findByUserIdAndChapterId(user.getId(), chapter.getId())
        .orElse(UserChapterGrade.builder().grade(chapterGrade).user(user).chapter(chapter).build());
    cg.setGrade(chapterGrade);
    chapterGradeRepo.save(cg);


    // Course grade logic.
    float courseGrade = computeCourseGrade(user.getId(), course.getId());
    UserCourseEnrollment uce = courseEnrollmentRepo.findByUserIdAndCourseId(user.getId(), course.getId())
        .orElseThrow(() -> new IllegalArgumentException("User is not enrolled in a course"));
    uce.setFinal_grade(courseGrade);
    courseEnrollmentRepo.save(uce);

  }

  public float computeSectionGrade(UUID userId, UUID sectionId) {
    List<UserProgress> progresses = upRepo.findByUserIdAndPage_Section_Id(userId, sectionId);
    return calculateGrade(progresses);
  }

  public float computeChapterGrade(UUID userId, UUID chapterId) {
    List<UserProgress> progresses = upRepo.findByUserIdAndPage_Section_Chapter_Id(userId, chapterId);
    return calculateGrade(progresses);
  }

  public float computeCourseGrade(UUID userId, UUID courseId) {
    List<UserProgress> progresses = upRepo.findByUserIdAndPage_Section_Chapter_Course_Id(userId, courseId);
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

  // ==================== DELETE ====================

  public void removeAllGrades(UUID userId, UUID courseId) {
    sectionGradeRepo.deleteByUserIdAndSection_Chapter_Course_Id(userId, courseId);
    chapterGradeRepo.deleteByUserIdAndChapter_Course_Id(userId, courseId);
    courseEnrollmentRepo.deleteByUserIdAndCourseId(userId, courseId);
  }
}
