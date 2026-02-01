package com.calcmadeeasy.models.Pages;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

@Entity
public class Page { 
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String content; // Latex markdown text

  @ManyToOne(fetch = FetchType.LAZY) // Many pages belong to one section
  @JoinColumn(name = "section_id")
  @JsonBackReference
  private Section section;

  private Integer position;

  @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PageProblem> problems;

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  protected Page() {
    this.problems = new ArrayList<>();
  }

  public static class Builder {
    private String content;
    private List<PageProblem> problems;
    private Integer position;

    public Builder content(String content) {
      this.content = content;
      return this;
    }

    public Builder position(Integer position) {
      this.position = position;
      return this;
    }

    public Page build() {
      return new Page(this);
    }
  }

  private Page(Builder b) {
    this.content = b.content;
    this.problems = b.problems == null ? new ArrayList<>() : new ArrayList<>(b.problems);
    this.position = b.position;
  }

  // Getters
  public UUID getId() {
    return id;
  }

  // Returns a LaTeX markdown text as a string
  public String getContent() {
    return content;
  }

  public int getProblemQuantity() {
    return problems.size();
  }

  public Integer getPosition() {
    return position;
  }

  public Section getSection() {
    return section;
  }

  // /*
  //  * Returns a list of problems that satisfy the attribute inputted.
  //  * 
  //  * @params Function<Problem, T> attributeExtractor = lamba
  //  * expression that is sending a problem related function for data extraction.
  //  * T value = value using to search for problems to retrieve.
  //  */
  // public <T> List<Problem> getProblemsBy(
  //     Function<Problem, T> attributeExtractor,
  //     T value) {

  //   return problems.stream()
  //       .map(PageProblem::getProblem)
  //       .filter(p -> Objects.equals(attributeExtractor.apply(p), value))
  //       .toList();
  // }

  // /*
  //  * Returns a list of problems by containing a certain value.
  //  * 
  //  * @params Function<Problem, Collection<T>> attributeExtractor = lamba
  //  * expression that is sending an arbitrary type collection for data extraction
  //  * within this generic method.
  //  * T value = value using to search for problems to retrieve.
  //  */
  // public <T> List<Problem> getProblemsContaining(
  //     Function<Problem, Collection<T>> attributeExtractor,
  //     T value) {
  //   return problems.stream()
  //       .map(PageProblem::getProblem)
  //       .filter(p -> attributeExtractor.apply(p).contains(value))
  //       .toList();
  // }

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

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  // Setters

  public void setContent(String newContent) {
    this.content = newContent;
  }

  public void addProblem(Problem problem, ProblemType type) {
    PageProblem pp = new PageProblem(this, problem, type);
    problems.add(pp);
  }

  public void setSection(Section section) {
    this.section = section;
  }

  public void setPosition(Integer newPosition) {
    this.position = newPosition;
  }

  // Removers

  // Removes a problem from the page (eg. exercises or homeworks)
  public void removeProblem(UUID problemId) {
    for (PageProblem p : problems) {
      if (p.getProblem().getId().equals(problemId)) {
        problems.remove(p);
        System.out.println("Removed " + p.getProblem().toString() + " from " + p.getType() + " set.");
        return;
      }
    }
  }

  public void removeAllProblems() {
    this.problems = new ArrayList<>();
  }

  @Override
  public String toString() {
    return "\nPage{\n" +
        "id=" + id +
        ", content=" + content +
        ", position=" + position +
        ", problems=" + problems.toString() +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "\n}";
  }

  // Allows for simplicity when creating a new Page object
  public static Builder builder() {
    return new Builder();
  }
}
