package com.calcmadeeasy.models.Problems;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.calcmadeeasy.exceptions.TagNotOnProblemException;
import com.calcmadeeasy.models.Pages.PageProblem;
import com.calcmadeeasy.models.Tags.Tag;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "problems")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Problem {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "Problem cannot be blank.")
  private String description;
 
  @NotNull(message = "Points cannot be null.")
  @PositiveOrZero(message = "Points must be 0 or greater postive integer.")
  private Integer points;
  private Boolean isChallenge;
  private String solution;

  // NUMERICAL or EXPRESSION
  @Enumerated(EnumType.STRING)
  private ProblemSolutionType solutionType;

  @Builder.Default
  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinTable(name = "problem_tag", 
            joinColumns = @JoinColumn(name = "problem_id"), 
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags = new HashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<PageProblem> pageProblems = new HashSet<>();

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  // Getters

  public Tag getTagById(UUID tagId) {
    return tags.stream()
      .filter(tag -> tag.getId().equals(tagId))
      .findFirst()
      .orElse(null);
  }

  // Setters

  public void setSolutionType(ProblemSolutionType newSolutionType) {
    if (newSolutionType != ProblemSolutionType.NUMERICAL && newSolutionType != ProblemSolutionType.EXPRESSION) {
      System.err.println("ERROR: Can only assign NUMERICAL or EXPRESSION solution types.");
      return;
    }
    this.solutionType = Objects.requireNonNull(newSolutionType);
  }

  public void addTag(Tag newTag) {
    Objects.requireNonNull(newTag, "Tag cannot be null");
    this.tags.add(newTag);
    // No bidirectional sync needed.
  }

  // Removers

  public void removeTag(UUID tagId) {
    Tag tag = tags.stream()
        .filter(t -> t.getId().equals(tagId))
        .findFirst()
        .orElseThrow(() -> new TagNotOnProblemException("Tag with id " + tagId + " is not associated with this problem"));

    tags.remove(tag);
  }
}
