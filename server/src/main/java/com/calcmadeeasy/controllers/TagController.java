package com.calcmadeeasy.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calcmadeeasy.dto.Tags.CreateTagDTO;
import com.calcmadeeasy.dto.Tags.TagDTO;
import com.calcmadeeasy.services.TagServices;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
  private final TagServices tagService;

  public TagController(TagServices tagService) {
    this.tagService = tagService;
  }

  // ---------------- CREATE ----------------

  @PostMapping
  public ResponseEntity<TagDTO> createTag(@RequestBody CreateTagDTO newTag) {
    TagDTO savedTag = tagService.createTag(newTag);
    return ResponseEntity.ok(savedTag);
  }

  // ---------------- READ ----------------

  @GetMapping
  public ResponseEntity<List<TagDTO>> getAllTags() {
    List<TagDTO> tags = tagService.getAllTags();
    return ResponseEntity.ok(tags);
  }

  @GetMapping("/{tagId}")
  public ResponseEntity<TagDTO> getTag(@PathVariable UUID tagId) {
    TagDTO t = tagService.getTagById(tagId);
    return ResponseEntity.ok(t);
  }
}
