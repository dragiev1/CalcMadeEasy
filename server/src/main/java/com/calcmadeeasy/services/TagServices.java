package com.calcmadeeasy.services;

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
  public Tag getOrCreateTag(String tag, double difficulty) {
    return repo.findByTag(tag).orElseGet(() -> repo.save(new Tag(tag, difficulty)));
  }

  public Tag createTag(Tag tag) {
    return repo.save(tag);
  }

  public List<Tag> createTag(Tag... tags) {
    return repo.saveAll(Arrays.asList(tags));
  }

  // ==================== READ ====================

  public List<Tag> getAllTags() {
    return repo.findAll();
  }

  public Tag getTagById(UUID tagId) {
    return repo.findById(tagId).orElseThrow(() -> new RuntimeException("No tag found with id: " + tagId));
  }

  public List<Tag> getTagsByDifficultyRange(double lowerBound, double upperBound) {
    if (upperBound > 1 || lowerBound < 0)
      throw new IllegalArgumentException("Unacceptable bounds range inputted");

    return repo.findAll().stream()
        .filter(t -> t.getDifficulty() >= lowerBound && t.getDifficulty() <= upperBound)
        .toList();
  }

  // ==================== UPDATE ====================

  public void updateDifficultyById(UUID tagId, double newDifficulty) {
    if(newDifficulty > 1 || newDifficulty < 0) throw new IllegalArgumentException("Difficulty must be a decimal between 0 and 1");
    Tag tag = getTagById(tagId);
    tag.setDifficulty(newDifficulty);
    repo.save(tag);
  }

  public void updateNameById(UUID tagId, String name) {
    if(name.isEmpty() || name.equals(" ")) throw new IllegalArgumentException("Cannot assign empty name to a tag");
    Tag tag = getTagById(tagId);
    tag.setTagName(name);
    repo.save(tag);
  }

  // ==================== DELETE ====================

  public void deleteTagById(UUID tagId) {
    if(!(repo.existsById(tagId))) throw new RuntimeException("CANNOT DELETE - tag does not exist with id: " + tagId);
    repo.deleteById(tagId);
  }

}
