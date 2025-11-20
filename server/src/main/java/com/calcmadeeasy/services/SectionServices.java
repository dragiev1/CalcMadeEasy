package com.calcmadeeasy.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.calcmadeeasy.dto.Sections.CreateSectionDTO;
import com.calcmadeeasy.dto.Sections.SectionResponseDTO;
import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Sections.Section;
import com.calcmadeeasy.repository.SectionRepo;

@Service
public class SectionServices {
  private final SectionRepo repo;

  public SectionServices(SectionRepo repo) {
    this.repo = repo;
  }

  // ==================== CREATE ====================

  public SectionResponseDTO createSection(CreateSectionDTO section) {
    Section s = Section.builder()
        .title(section.getTitle())
        .description(section.getDescription())
        .build();

    repo.save(s);

    return new SectionResponseDTO(s);
  }

  public List<Section> createSections(Section... sections) {
    return repo.saveAll(Arrays.asList(sections));
  }

  public List<Section> createSections(List<Section> sections) {
    return repo.saveAll(sections);
  }

  public Section addPage(Section section, Page page) {
    Section s = getSectionById(section.getId());
    s.getPages().add(page);
    return repo.save(s);
  }

  // ==================== READ ====================

  public Section getSectionById(UUID sectionId) {
    return repo.findById(sectionId).orElseThrow(() -> new RuntimeException("No section is "));
  }

  public List<Section> getAllSections() {
    return repo.findAll();
  }

  public boolean exists(UUID sectionId) {
    return repo.existsById(sectionId);
  }

  // ==================== UPDATE ====================

  public void updateDescription(UUID sectionId, String newDesc) {
    Section section = getSectionById(sectionId);
    section.setDescription(newDesc);
    repo.save(section);
  }

  public void updateTitle(UUID sectionId, String newTitle) {
    Section section = getSectionById(sectionId); // Get section
    section.setTitle(newTitle); // Set title
    repo.save(section); // Save section
  }

  // ==================== DELETE ====================

  public void removeSection(UUID sectionId) {
    if (!exists(sectionId))
      throw new IllegalArgumentException("Section does not exist, cannot remove");
    repo.deleteById(sectionId);
    if (exists(sectionId))
      throw new IllegalArgumentException("Section was found but page was not removed");
  }

  public void removePage(UUID sectionId, UUID pageId) {
    if (!exists(sectionId))
      throw new IllegalArgumentException("Section does not exist, cannot remove page");

    Section section = getSectionById(sectionId);
    boolean removed = section.getPages().removeIf(p -> p.getId().equals(pageId));

    if (!removed)
      throw new IllegalArgumentException("Section was found but page was not removed");
  }
}
