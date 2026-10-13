package com.calcmadeeasy.services;

import com.calcmadeeasy.dto.Tags.CreateTagDTO;
import com.calcmadeeasy.dto.Tags.TagDTO;
import com.calcmadeeasy.dto.Tags.UpdateTagDTO;
import com.calcmadeeasy.exceptions.TagNotFoundException;
import com.calcmadeeasy.models.Tags.Tag;
import com.calcmadeeasy.repository.TagRepo;

import org.springframework.transaction.annotation.Transactional; 

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class TagService {
  private final TagRepo repo;

  public TagService(TagRepo repo) {
    this.repo = repo;
  }

  // ==================== CREATE ====================

  /* @Transactional should be used when creating/modifying data. So when transactions do not fully complete, e.g.
                    DB write didn't complete, it does not corrupt anything as it was not saved yet. */

  @Transactional
  public Tag getOrCreateTag(String tagName, Double difficulty) {
    return repo.findByTagName(tagName).orElseGet(() -> repo.save(Tag.builder().difficulty(difficulty).tagName(tagName).build()));
  }

  @Transactional
  public TagDTO createTag(CreateTagDTO tag) {
    if (tag == null)
      throw new IllegalArgumentException("Cannot create tag with null value");

    Tag t = Tag.builder().tagName(tag.getName()).difficulty(tag.getDifficulty()).build();
    repo.save(t);

    return new TagDTO(t);
  }

  @Transactional
  public List<Tag> createTags(List<Tag> tags) {
    return repo.saveAll(tags);
  }
  
  @Transactional
  public List<Tag> createTags(Tag... tags) {
    return repo.saveAll(Arrays.asList(tags));
  }

  // ==================== READ ====================

  @Transactional(readOnly = true)
  public List<TagDTO> getAllTags() {
    return repo.findAll().stream()
        .map(TagDTO::new)
        .toList();
  }

  @Transactional(readOnly = true)
  public TagDTO getTag(UUID tagId) {
    Tag t = repo.findById(tagId)
        .orElseThrow(() -> new TagNotFoundException("No tag found with id: " + tagId));

    return new TagDTO(t);
  }

  @Transactional(readOnly = true)
  public Tag getTagEntity(UUID tagId) {
    return repo.findById(tagId)
        .orElseThrow(() -> new TagNotFoundException("No tag found with id: " + tagId));
  }

  @Transactional(readOnly = true)
  public List<Tag> getTagsByDifficultyRange(Double lowerBound, Double upperBound) {
    if (upperBound > 1 || lowerBound < 0)
      throw new IllegalArgumentException("Unacceptable bounds range inputted");

    // Fix/Tip: Use the method made inside of TagRepo, don't filter here. Let DB do it; not the backend.  
    return repo.findByDifficultyBetween(lowerBound, upperBound);
  }

  @Transactional(readOnly = true)
  public boolean exists(UUID tagId) {
    return repo.existsById(tagId);
  }

  // ==================== UPDATE ====================

  @Transactional
  public TagDTO updateTag(UUID tagId, UpdateTagDTO request) {
    Tag tag = getTagEntity(tagId);
    
    if (request.getDifficulty() != null)
      tag.setDifficulty(request.getDifficulty());
    if (request.getName() != null)
      tag.setTagName(request.getName());

    if (request.getDifficulty() > 1 || request.getDifficulty() < 0)
      throw new IllegalArgumentException("Difficulty must be a decimal between 0 and 1");

    repo.save(tag);

    return new TagDTO(tag);
  }

  // ==================== DELETE ====================

  @Transactional
  public void removeTagById(UUID tagId) {
    if (!(repo.existsById(tagId)))
      throw new TagNotFoundException("CANNOT REMOVE: tag does not exist with id: " + tagId);
    repo.deleteById(tagId);
  }

}
