package com.calcmadeeasy.services;

import com.calcmadeeasy.dto.Tags.CreateTagDTO;
import com.calcmadeeasy.dto.Tags.TagDTO;
import com.calcmadeeasy.models.Tags.Tag;
import com.calcmadeeasy.repository.TagRepo;

import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class TagServices {
  private final TagRepo repo;

  public TagServices(TagRepo repo) {
    this.repo = repo;
  }

  // ==================== CREATE ====================

  @Transactional
  public Tag getOrCreateTag(String tagName, Double difficulty) {
    return repo.findByTagName(tagName).orElseGet(() -> repo.save(new Tag(tagName, difficulty)));
  }

  public TagDTO createTag(CreateTagDTO tag) {
    if (tag == null)
      throw new IllegalArgumentException("Cannot create tag with null value");

    Tag t = new Tag(tag.getName(), tag.getDifficulty());
    repo.save(t);

    return new TagDTO(t);
  }

  public List<Tag> createTags(List<Tag> tags) {
    return repo.saveAll(tags);
  }

  public List<Tag> createTags(Tag... tags) {
    return repo.saveAll(Arrays.asList(tags));
  }

  // ==================== READ ====================

  public List<TagDTO> getAllTags() {
    return repo.findAll().stream()
        .map(TagDTO::new)
        .toList();
  }

  public TagDTO getTag(UUID tagId) {
    Tag t = repo.findById(tagId)
        .orElseThrow(() -> new RuntimeException("No tag found with id: " + tagId));

    return new TagDTO(t);
  }

  public Tag getTagEntity(UUID tagId) {
    return repo.findById(tagId)
        .orElseThrow(() -> new RuntimeException("No tag found with id: " + tagId));
  }

  public List<Tag> getTagsByDifficultyRange(Double lowerBound, Double upperBound) {
    if (upperBound > 1 || lowerBound < 0)
      throw new IllegalArgumentException("Unacceptable bounds range inputted");

    return repo.findAll().stream()
        .filter(t -> t.getDifficulty() >= lowerBound && t.getDifficulty() <= upperBound)
        .toList();
  }

  public boolean exists(UUID tagId) {
    return repo.existsById(tagId);
  }

  // ==================== UPDATE ====================

  public TagDTO updateTag(UUID tagId, TagDTO request) {
    if (request.getDifficulty() > 1 || request.getDifficulty() < 0)
      throw new IllegalArgumentException("Difficulty must be a decimal between 0 and 1");

    Tag tag = getTagEntity(tagId);

    if (request.getDifficulty() != null)
      tag.setDifficulty(request.getDifficulty());
    if (request.getTagName() != null)
      tag.setTagName(request.getTagName());

    repo.save(tag);

    return new TagDTO(tag);
  }

  // ==================== DELETE ====================

  public void deleteTagById(UUID tagId) {
    if (!(repo.existsById(tagId)))
      throw new RuntimeException("CANNOT DELETE - tag does not exist with id: " + tagId);
    repo.deleteById(tagId);
  }

}
