package com.calcmadeeasy.services;

import com.calcmadeeasy.dto.Chapters.ChapterDTO;
import com.calcmadeeasy.dto.Chapters.ChapterResponseDTO;
import com.calcmadeeasy.dto.Chapters.CreateChapterDTO;
import com.calcmadeeasy.dto.Chapters.UpdateChapterDTO;
import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.repository.ChapterRepo;
import com.calcmadeeasy.repository.CourseRepo;
import com.calcmadeeasy.repository.SectionRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ChapterServices {
  private final CourseRepo courseRepo;
  private final ChapterRepo repo;
  private final SectionRepo sectionRepo;

  public ChapterServices(CourseRepo courseRepo, ChapterRepo repo, SectionRepo sectionRepo) {
    this.courseRepo = courseRepo;
    this.repo = repo;
    this.sectionRepo = sectionRepo;
  }

  // ==================== CREATE ====================

  public ChapterResponseDTO createChapter(CreateChapterDTO chapter) {
    if (chapter == null)
      throw new IllegalArgumentException("Cannot save null chapter");

    Chapter c = Chapter.builder()
        .description(chapter.getDescription())
        .title(chapter.getTitle())
        .build();

    Course co = courseRepo.findById(chapter.getCourseId())
        .orElseThrow(() -> new IllegalArgumentException("Could not find course to set inside chapter"));
    c.setCourse(co);

    repo.save(c);
    return new ChapterResponseDTO(c);
  }

  // ==================== READ ====================

  public ChapterDTO getChapterDTO(UUID chapterId) {
    Chapter c = repo.findById(chapterId)
        .orElseThrow(() -> new IllegalArgumentException("Cannot find chapter with id: " + chapterId));

    return new ChapterDTO(c);
  }

  public Chapter getChapterEntity(UUID chapterId) {
    return repo.findById(chapterId)
        .orElseThrow(() -> new IllegalArgumentException("Cannot find chapter with id: " + chapterId));
  }

  public List<ChapterDTO> getAllChapters() {
    return repo.findAll().stream()
        .map(ChapterDTO::new).toList();
  }

  public boolean exists(UUID chapterId) {
    return repo.existsById(chapterId);
  }

  // ==================== UPDATE ====================

  public ChapterResponseDTO updateChapter(UUID chapterId, UpdateChapterDTO request) {
    if (!exists(chapterId))
      throw new IllegalArgumentException("Chapter does not exist");

    Chapter c = getChapterEntity(chapterId);

    if (request.getDescription() != null)
      c.setDescription(request.getDescription());
    if (request.getTitle() != null)
      c.setTitle(request.getTitle());

    repo.save(c);
    return new ChapterResponseDTO(c);
  }

  public ChapterDTO moveSection(UUID chapterId, UUID sectionId) {
    Chapter target = getChapterEntity(chapterId);
    Section s = sectionRepo.findById(sectionId)
        .orElseThrow(() -> new EntityNotFoundException("No section is found with id: " + sectionId));
    Chapter current = s.getChapter();

    if (current == null)
      throw new IllegalStateException("Section is not attached to courseId" + chapterId);

    if (current.getId().equals(chapterId))
      return new ChapterDTO(current);

    current.getSections().remove(s);

    target.addSection(s);
    repo.save(target);
    repo.save(current);
    return new ChapterDTO(target);
  }

  // ==================== DELETE ====================

  @Transactional
  public void removeChapter(UUID chapterId) {
    repo.deleteById(chapterId);
  }

  public ChapterDTO removeSection(UUID chapterId, UUID sectionId) {
    if (!exists(chapterId))
      throw new RuntimeException("Chapter does not exist");
    Chapter c = getChapterEntity(chapterId);
    boolean removed = c.getSections().removeIf(s -> s.getId().equals(sectionId));
    if (!removed)
      throw new IllegalArgumentException("Section does not exist in Chapter, could not remove");
    repo.save(c);
    return new ChapterDTO(c);
  }

}
