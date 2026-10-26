package com.calcmadeeasy.services;

import com.calcmadeeasy.dto.Chapters.ChapterDTO;
import com.calcmadeeasy.dto.Chapters.ChapterResponseDTO;
import com.calcmadeeasy.dto.Chapters.CreateChapterDTO;
import com.calcmadeeasy.dto.Chapters.UpdateChapterDTO;
import com.calcmadeeasy.exceptions.ChapterNotFoundException;
import com.calcmadeeasy.exceptions.SectionNotFoundException;
import com.calcmadeeasy.exceptions.SectionNotOnChapterException;
import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.repository.ChapterRepo;
import com.calcmadeeasy.repository.CourseRepo;
import com.calcmadeeasy.repository.SectionRepo;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChapterService {
  private final CourseRepo courseRepo;
  private final ChapterRepo repo;
  private final SectionRepo sectionRepo;

  public ChapterService(CourseRepo courseRepo, ChapterRepo repo, SectionRepo sectionRepo) {
    this.courseRepo = courseRepo;
    this.repo = repo;
    this.sectionRepo = sectionRepo;
  }

  // ==================== CREATE ====================

  @Transactional
  public ChapterResponseDTO createChapter(CreateChapterDTO chapter) {
    Chapter c = Chapter.builder()
        .description(chapter.getDescription())
        .title(chapter.getTitle())
        .build();

    Course co = courseRepo.findById(chapter.getCourseId())
        .orElseThrow(() -> new ChapterNotFoundException("Could not find course to set inside chapter"));
    c.setCourse(co);

    repo.save(c);
    return new ChapterResponseDTO(c);
  }

  // ==================== READ ====================

  @Transactional(readOnly = true)
  public ChapterDTO getChapterDTO(UUID chapterId) {
    Chapter c = repo.findById(chapterId)
        .orElseThrow(() -> new ChapterNotFoundException("Cannot find chapter with id: " + chapterId));

    return new ChapterDTO(c);
  }

  @Transactional(readOnly = true)
  public Chapter getChapterEntity(UUID chapterId) {
    return repo.findById(chapterId)
        .orElseThrow(() -> new ChapterNotFoundException("Cannot find chapter with id: " + chapterId));
  }

  @Transactional(readOnly = true)
  public List<ChapterDTO> getAllChapters() {
    return repo.findAll().stream()
        .map(ChapterDTO::new).toList();
  }

  @Transactional(readOnly = true)
  public boolean exists(UUID chapterId) {
    return repo.existsById(chapterId);
  }

  // ==================== UPDATE ====================
  
  @Transactional
  public ChapterResponseDTO updateChapter(UUID chapterId, UpdateChapterDTO request) {
    if (!exists(chapterId))
      throw new ChapterNotFoundException("Chapter does not exist");

    Chapter c = getChapterEntity(chapterId);

    if (request.getDescription() != null)
      c.setDescription(request.getDescription());
    if (request.getTitle() != null)
      c.setTitle(request.getTitle());

    repo.save(c);
    return new ChapterResponseDTO(c);
  }

  @Transactional
  public ChapterDTO moveSection(UUID chapterId, UUID sectionId) {
    Chapter target = getChapterEntity(chapterId);
    Section s = sectionRepo.findById(sectionId)
        .orElseThrow(() -> new SectionNotFoundException("No section is found with id: " + sectionId));
    Chapter current = s.getChapter();

    if (current == null)
      throw new SectionNotOnChapterException("Section is not attached to chapterId" + chapterId);

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
    if(!exists(chapterId)) throw new ChapterNotFoundException("Cannot delete: Chapter not found with id: "  + chapterId);
    repo.deleteById(chapterId);
  }

  @Transactional
  public ChapterDTO removeSection(UUID chapterId, UUID sectionId) {
    if (!exists(chapterId))
      throw new RuntimeException("Chapter does not exist");
    Chapter c = getChapterEntity(chapterId);

    boolean removed = c.getSections().removeIf(s -> s.getId().equals(sectionId));

    if (!removed)
      throw new SectionNotOnChapterException("Section does not exist in Chapter, could not remove");

    repo.save(c);
    return new ChapterDTO(c);
  }
}
