package com.calcmadeeasy.services;

import com.calcmadeeasy.models.Tags.Tag;
import com.calcmadeeasy.repository.TagRepo;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class TagServices {
  private final TagRepo repo;

  public TagServices(TagRepo repo) {
    this.repo = repo;
  }

  @Transactional
  public Tag getOrCreateTag(String tag, double difficulty) {
    return repo.findByTag(tag).orElseGet(() -> repo.save(new Tag(tag, difficulty)));
  }
}
