package com.calcmadeeasy.dto.Problems;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.calcmadeeasy.dto.Tags.TagDTO;
import com.calcmadeeasy.models.Problems.Problem;

// Outbound only.
public class ProblemDTO extends ProblemResponseDTO {
  private Set<TagDTO> tags;
  private Instant updatedAt;

  public ProblemDTO(Problem problem) {
    super(problem);
    this.tags = problem.getTags().stream()
        .map(TagDTO::new)
        .collect(Collectors.toSet());
    this.updatedAt = problem.getUpdatedAt();
  }

  // Getters

  public Set<TagDTO> getTags() {
    return tags;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public TagDTO getTagById(UUID tagId) {
    return tags.stream()
        .filter(tag -> tag.getId().equals(tagId))
        .findFirst()
        .orElse(null);
  }

}
