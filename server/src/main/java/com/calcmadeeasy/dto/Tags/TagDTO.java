package com.calcmadeeasy.dto.Tags;

import java.util.UUID;

import com.calcmadeeasy.models.Tags.Tag;

// Outbound only.
public class TagDTO {
  private UUID id;
  private String name;
  private double difficulty;

  // No args constructor for Jackson.
  public TagDTO() {
  }

  public TagDTO(Tag t) {
    this.id = t.getId();
    this.difficulty = t.getDifficulty();
    this.name = t.getTagName();
  }

  // Getters

  public UUID getId() {
    return id;
  }

  public String getTagName() {
    return name;
  }

  public double getDifficulty() {
    return difficulty;
  }

  // No setters.
}
