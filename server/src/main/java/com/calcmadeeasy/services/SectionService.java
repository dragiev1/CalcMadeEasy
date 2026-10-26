package com.calcmadeeasy.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calcmadeeasy.dto.Sections.CreateSectionDTO;
import com.calcmadeeasy.dto.Sections.SectionDTO;
import com.calcmadeeasy.dto.Sections.SectionResponseDTO;
import com.calcmadeeasy.dto.Sections.UpdateSectionDTO;
import com.calcmadeeasy.exceptions.ChapterNotFoundException;
import com.calcmadeeasy.exceptions.SectionNotFoundException;
import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.repository.ChapterRepo;
import com.calcmadeeasy.repository.SectionRepo;

@Service
public class SectionService {
  private final ChapterRepo chapterRepo;
  private final SectionRepo repo;
  private final PageService pageServices;

  public SectionService(ChapterRepo chapterRepo, SectionRepo repo, PageService pageServices) {
    this.chapterRepo = chapterRepo;
    this.repo = repo;
    this.pageServices = pageServices;
  }

  // ==================== CREATE ====================

  @Transactional
  public SectionResponseDTO createSection(CreateSectionDTO section) {
    Section s = Section.builder()
        .title(section.getTitle())
        .description(section.getDescription())
        .build();

    Chapter c = chapterRepo.findById(section.getChapterId())
        .orElseThrow(() -> new ChapterNotFoundException("Cannot find chapter with id " + section.getChapterId() + " to assign section to"));
    s.setChapter(c);

    repo.save(s);
    return new SectionResponseDTO(s);
  }

  @Transactional
  public List<Section> createSections(Section... sections) {
    return repo.saveAll(Arrays.asList(sections));
  }

  @Transactional
  public List<Section> createSections(List<Section> sections) {
    return repo.saveAll(sections);
  }

  // ==================== READ ====================

  @Transactional(readOnly = true)
  public SectionDTO getSectionDTO(UUID sectionId) {
    Section s = repo.findById(sectionId)
        .orElseThrow(() -> new SectionNotFoundException("No section is found with id: " + sectionId));

    return new SectionDTO(s);
  }

  @Transactional(readOnly = true)
  public Section getSectionEntity(UUID sectionId) {
    return repo.findById(sectionId)
        .orElseThrow(() -> new SectionNotFoundException("No section is found with id: " + sectionId));
  }

  @Transactional(readOnly = true)
  public List<SectionDTO> getAllSections() {
    return repo.findAll()
        .stream()
        .map(SectionDTO::new)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<Section> getAllSectionEntities() {
    return repo.findAll();
  }

  @Transactional(readOnly = true)
  public boolean exists(UUID sectionId) {
    return repo.existsById(sectionId);
  }

  // ==================== UPDATE ====================

  @Transactional
  public SectionResponseDTO updateSection(UUID sectionId, UpdateSectionDTO request) {
    Section s = getSectionEntity(sectionId);

    if (request.getDescription() != null)
      s.setDescription(request.getDescription());
    if (request.getTitle() != null)
      s.setTitle(request.getTitle());

    repo.save(s);

    return new SectionResponseDTO(s);
  }

  @Transactional
  public SectionDTO addPage(UUID sectionId, UUID pageId) {
    Section s = getSectionEntity(sectionId);
    Page p = pageServices.getPageEntity(pageId);
    p.setPosition(s.getPages().size() + 1);
    s.addPage(p);
    p.setSection(s);
    repo.save(s);

    return new SectionDTO(s);
  }

  // ==================== DELETE ====================

  @Transactional
  public void removeSection(UUID sectionId) {
    getSectionEntity(sectionId).getChapter().removeSection(sectionId);
    repo.deleteById(sectionId);

    if (exists(sectionId))
      throw new IllegalArgumentException("Section persists after deletion");
  }

  @Transactional
  public SectionDTO removePage(UUID sectionId, UUID pageId) {
    if (!exists(sectionId))
      throw new SectionNotFoundException("Section does not exist, cannot remove page");

    Section s = getSectionEntity(sectionId);
    Page p = pageServices.getPageEntity(pageId);
    s.removePage(pageId);
    p.setSection(s);

    repo.save(s);
    return new SectionDTO(s);
  }
}
