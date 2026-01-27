package com.calcmadeeasy.services;

import com.calcmadeeasy.dto.Chapters.ChapterDTO;
import com.calcmadeeasy.dto.Chapters.ChapterResponseDTO;
import com.calcmadeeasy.dto.Chapters.CreateChapterDTO;
import com.calcmadeeasy.dto.Chapters.UpdateChapterDTO;
import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.repository.ChapterRepo;
import com.calcmadeeasy.repository.SectionRepo;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ChapterServices {
  private final ChapterRepo repo;
  private final SectionRepo sectionRepo;

  public ChapterServices(ChapterRepo repo, SectionRepo sectionRepo) {
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

  public ChapterDTO updateChapter(UUID chapterId, UpdateChapterDTO request) {
    if(!exists(chapterId)) throw new IllegalArgumentException("Chapter does not exist");

    Chapter c = getChapterEntity(chapterId);

    if(request.getDescription() != null) c.setDescription(request.getDescription());
    if(request.getTitle() != null) c.setTitle(request.getTitle());

    repo.save(c);
    return new ChapterDTO(c);
  }

  public ChapterDTO addSection(UUID chapterId, UUID sectionId) {
    Chapter c = getChapterEntity(chapterId);
    Section s = sectionRepo.findById(sectionId).orElseThrow(() -> new RuntimeException("No section is found with id: " + sectionId));
    s.setChapter(c);
    c.addSection(s);
    repo.save(c);
    return new ChapterDTO(c);
  }

  // ==================== DELETE ====================

  public void removeChapter(UUID chapterId) {
    repo.deleteById(chapterId);
  }

  public void removeSection(UUID chapterId, UUID sectionId) {
    if (!exists(chapterId))
      throw new RuntimeException("Chapter does not exist");
    Chapter c = getChapterEntity(chapterId);
    boolean removed = c.getSections().removeIf(s -> s.getId().equals(sectionId));
    if (!removed)
      throw new IllegalArgumentException("Section does not exist in Chapter, could not delete");
    repo.save(c);
  }

}
