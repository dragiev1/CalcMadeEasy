package com.calcmadeeasy.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.calcmadeeasy.dto.Sections.CreateSectionDTO;
import com.calcmadeeasy.dto.Sections.SectionDTO;
import com.calcmadeeasy.dto.Sections.SectionResponseDTO;
import com.calcmadeeasy.dto.Sections.UpdateSectionDTO;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.repository.SectionRepo;

@Service
public class SectionServices {
  private final SectionRepo repo;
  private PageServices pageService;
  private ChapterServices chapterService;

  public SectionServices(SectionRepo repo, PageServices pageService, ChapterServices chapterService) {
    this.repo = repo;
    this.pageService = pageService;
    this.chapterService = chapterService;
  }

  // ==================== CREATE ====================

  public SectionResponseDTO createSection(CreateSectionDTO section) {
    Section s = Section.builder()
        .title(section.getTitle())
        .description(section.getDescription())
        .build();

    s.setChapter(chapterService.getChapter(section.getChapterId()));
    
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
    Page p = pageService.getPageEntity(pageId);
    p.setPosition(pageService.getPagesBySection(sectionId).size() + 1);
    s.getPages().add(p);

    repo.save(s);

    return new SectionDTO(s);
  }

  // ==================== DELETE ====================

  public void deleteSection(UUID sectionId) {
    boolean exists = exists(sectionId);
    if (!exists)
      throw new IllegalArgumentException("Section does not exist, cannot remove");

    repo.deleteById(sectionId);

    if (exists)
      throw new IllegalArgumentException("Section was found but was not deleted");
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
