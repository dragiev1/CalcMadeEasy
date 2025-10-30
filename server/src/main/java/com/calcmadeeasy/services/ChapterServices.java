package com.calcmadeeasy.services;

import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.repository.ChapterRepo;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ChapterServices {
  private final ChapterRepo repo;

  public ChapterServices(ChapterRepo repo) {
    this.repo = repo;
  }

  // ==================== CREATE ====================

  public Chapter createChapter(Chapter chapter) {
    if (chapter == null)
      throw new IllegalArgumentException("Cannot save null chapter");
    return repo.save(chapter);
  }

  public List<Chapter> createChapters(Chapter... chapters) {
    if (chapters == null || chapters.length == 0)
      throw new IllegalArgumentException("Cannot save null chapters");
    return repo.saveAll(List.of(chapters));
  }

  public List<Chapter> createChapters(List<Chapter> chapters) {
    if (chapters == null || chapters.size() == 0)
      throw new IllegalArgumentException("Cannot save null chapters");
    return repo.saveAll(chapters);
  }

  // ==================== RETRIEVE ====================

  public Chapter getChapter(UUID chapterId) {
    return repo.findById(chapterId)
        .orElseThrow(() -> new IllegalArgumentException("Cannot find chapter with id: " + chapterId));
  }

  public List<Chapter> getAllChapters() {
    return repo.findAll();
  }

  public boolean exists(UUID chapterId) {
    return repo.existsById(chapterId);
  }

  // ==================== UPDATE ====================

  public void updateDescription(UUID chapterId, String newDesc) {
    if (!exists(chapterId))
      throw new RuntimeException("Chapter does not exist with id: " + chapterId);
    Chapter c = getChapter(chapterId);
    c.setDescription(newDesc);
    repo.save(c);
  }

  public void updateTitle(UUID chapterId, String newTitle) {
    Chapter c = getChapter(chapterId);
    c.setTitle(newTitle);
    repo.save(c);
  }

  public void addSection(UUID chapterId, Section section) {
    Chapter c = getChapter(chapterId);
    section.setChapter(c);
    c.addSection(section);
    repo.save(c);
  }

  // ==================== REMOVE ====================

  public void removeChapter(UUID chapterId) {
    repo.deleteById(chapterId);
  }

  public void removeSection(UUID chapterId, UUID sectionId) {
    if (!exists(chapterId))
      throw new RuntimeException("Chapter does not exist");
    Chapter c = getChapter(chapterId);
    boolean removed = c.getSections().removeIf(s -> s.getId().equals(sectionId));
    if(!removed) throw new IllegalArgumentException("Section does not exist in Chapter, could not delete");
    repo.save(c);
  }

}
