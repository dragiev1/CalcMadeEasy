package com.calcmadeeasy.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.calcmadeeasy.dto.Sections.CreateSectionDTO;
import com.calcmadeeasy.dto.Sections.SectionDTO;
import com.calcmadeeasy.dto.Sections.SectionResponseDTO;
import com.calcmadeeasy.dto.Sections.UpdateSectionDTO;
import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.repository.ChapterRepo;
import com.calcmadeeasy.repository.PageRepo;
import com.calcmadeeasy.repository.SectionRepo;

@Service
public class SectionServices {
  private final ChapterRepo chapterRepo;
  private final SectionRepo repo;
  private final PageRepo pageRepo;

  public SectionServices(ChapterRepo chapterRepo, SectionRepo repo, PageRepo pageRepo, ChapterServices chapterService) {
    this.chapterRepo = chapterRepo;
    this.repo = repo;
    this.pageRepo = pageRepo;
  }

  // ==================== CREATE ====================

  public SectionResponseDTO createSection(CreateSectionDTO section) {
    Section s = Section.builder()
        .title(section.getTitle())
        .description(section.getDescription())
        .build();
    
    Chapter c = chapterRepo.findById(section.getChapterId()).orElseThrow(() -> new IllegalArgumentException("Cannot find chapter to assign section to"));
    s.setChapter(c);

    repo.save(s);

    return new SectionResponseDTO(s);
  }

  public List<Section> createSections(Section... sections) {
    return repo.saveAll(Arrays.asList(sections));
  }

  public List<Section> createSections(List<Section> sections) {
    return repo.saveAll(sections);
  }

  // ==================== READ ====================

  public SectionDTO getSectionDTO(UUID sectionId) {
    Section s = repo.findById(sectionId).orElseThrow(() -> new RuntimeException("No section is found with id: " + sectionId));

    return new SectionDTO(s);
  }

  public Section getSectionEntity(UUID sectionId) {
    return repo.findById(sectionId).orElseThrow(() -> new RuntimeException("No section is found with id: " + sectionId));
  }

  public List<SectionDTO> getAllSections() {
    return repo.findAll()
        .stream()
        .map(SectionDTO::new)
        .toList();
  }

  public List<Section> getAllSectionEntities() {
    return repo.findAll();
  }

  public boolean exists(UUID sectionId) {
    return repo.existsById(sectionId);
  }

  // ==================== UPDATE ====================

  public SectionDTO updateSection(UUID sectionId, UpdateSectionDTO request) {
    Section s = getSectionEntity(sectionId);

    if (request.getDescription() != null)
      s.setDescription(request.getDescription());
    if (request.getTitle() != null)
      s.setTitle(request.getTitle());

    repo.save(s);

    return new SectionDTO(s);
  }

  public SectionDTO addPage(UUID sectionId, UUID pageId) {
    Section s = getSectionEntity(sectionId);
    Page p = pageRepo.findById(pageId).orElseThrow(() -> new IllegalArgumentException("Page does not exist to add to section"));
    p.setPosition(s.getPages().size() + 1);
    s.getPages().add(p);

    repo.save(s);

    return new SectionDTO(s);
  }

  // ==================== DELETE ====================

  public void removeSection(UUID sectionId) {
    repo.deleteById(sectionId);

    if(exists(sectionId)) throw new IllegalArgumentException("Section persists after deletion");
  }

  public SectionDTO removePage(UUID sectionId, UUID pageId) {
    if (!exists(sectionId))
      throw new IllegalArgumentException("Section does not exist, cannot remove page");

    Section s = getSectionEntity(sectionId);
    boolean removed = s.getPages().removeIf(p -> p.getId().equals(pageId));

    if (!removed)
      throw new IllegalArgumentException("Section was found but page was not removed");

    return new SectionDTO(s);
  }
}
