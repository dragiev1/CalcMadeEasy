package com.calcmadeeasy.models.Pages;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Problems.ProblemType;
import com.calcmadeeasy.models.Sections.Section;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Page { 
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String content; // Latex markdown text

  @ManyToOne(fetch = FetchType.LAZY) // Many pages belong to one section
  @JoinColumn(name = "section_id")
  @JsonBackReference
  private Section section;

  @Positive(message = "Page's position must be a positive integer.")
  private Integer position;

  @Builder.Default
  @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PageProblem> problems = new ArrayList<>();

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  public List<Problem> getHomework() {
    return problems.stream()
        .filter(p -> p.getType() == ProblemType.HOMEWORK)
        .map(PageProblem::getProblem)
        .toList();
  }

  public List<Problem> getExercises() {
    return problems.stream()
        .filter(p -> p.getType() == ProblemType.EXERCISE)
        .map(PageProblem::getProblem)
        .toList();
  }

  // Setters

  public void addProblem(Problem problem, ProblemType type) {
    Objects.requireNonNull(problem, "Problem cannot be null");
    Objects.requireNonNull(type, "ProblemType cannot be null");
    PageProblem pp = PageProblem.builder().page(this).problem(problem).type(type).build();
    problems.add(pp);
  }

  // Removers

  // Removes a problem from the page (eg. exercises or homework)
  public void removeProblem(UUID problemId) {
    boolean removed = this.problems.removeIf(p -> p.getProblem().getId().equals(problemId));
    if (removed) System.out.println("Removed problem " + problemId + " from page.");
  }

  public void removeAllProblems() {
    this.problems.clear();  // Safely empties the list while keeping hibernate happy
  }
}
