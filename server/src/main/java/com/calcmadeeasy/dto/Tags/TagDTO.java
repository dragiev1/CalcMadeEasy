package com.calcmadeeasy.dto.Tags;

import java.math.BigDecimal;
import java.util.UUID;

import com.calcmadeeasy.models.Tags.Tag;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

// Outbound only.
@Getter
public class TagDTO {
  @NotNull(message = "Tag ID cannot be null.")
  private UUID id;
  @NotBlank(message = "Tag name cannot be empty.")
  private String tagName;
  @DecimalMin(value = "0.0", message = "Difficulty must be at least 0.")
  @DecimalMax(value = "1.0", message = "Difficulty must not exceed 1.")
  private BigDecimal difficulty;

  public TagDTO(Tag t) {
    this.id = t.getId();
    this.difficulty = t.getDifficulty();
    this.tagName = t.getTagName();
  }
}
