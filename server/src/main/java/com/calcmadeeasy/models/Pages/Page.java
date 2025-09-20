package com.calcmadeeasy.models.Pages;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.calcmadeeasy.models.Problem.Problem;
import com.calcmadeeasy.models.Problem.ProblemType;
import com.calcmadeeasy.models.Sections.Section;

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
  private int problemQuantity;

  @ManyToOne(fetch = FetchType.LAZY) // Many pages belong to one section
  @JoinColumn(name = "section_id")
  private Section section;

  @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PageProblem> problems;

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  public Page() {
    this.problems = new ArrayList<>();
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }

  public static class Builder {
    private String content;
    private List<PageProblem> problems;
    private Instant createdAt;

    public Builder content(String content) {
      this.content = content;
      return this;
    }

    public Builder createdAt(Instant createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Page build() {
      return new Page(this);
    }
  }

  private Page(Builder b) {
    this.content = b.content;
    this.problems = b.problems == null ? new ArrayList<>() : new ArrayList<>(b.problems);
    this.problemQuantity = 0;
    this.createdAt = b.createdAt == null ? Instant.now() : b.createdAt;
    this.updatedAt = this.createdAt;
  }

  public void touch() {
    this.updatedAt = Instant.now();
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
    return problemQuantity;
  }

  /*
   * Returns a list of problems that satisfy the attribute inputted.
   * 
   * @params Function<Problem, T> attributeExtractor = lamba
   * expression that is sending a problem related function for data extraction.
   * T value = value using to search for problems to retrieve.
   */
  public <T> List<Problem> getProblemsBy(
      Function<Problem, T> attributeExtractor,
      T value) {

    return problems.stream()
        .map(PageProblem::getProblem)
        .filter(p -> Objects.equals(attributeExtractor.apply(p), value))
        .toList();
  }

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
    touch();
  }

  public void setProblem(Problem problem, ProblemType type) {
    PageProblem pp = new PageProblem(this, problem, type);
    problems.add(pp);
    problemQuantity++;
    touch();
  }

  // Removers

  // Removes a problem from the page (eg. exercises or homeworks)
  public void removeProblem(UUID problemId) {
    for (PageProblem p : problems) {
      if (p.getProblem().getId().equals(problemId)) {
        problems.remove(p);
        System.out.println("Removed " + p.getProblem().toString() + " from " + p.getType() + " set.");
        problemQuantity--;
        return;
      }
    }
  }

  @Override
  public String toString() {
    return "\nPage{\n" +
        "id=" + id +
        ", content=" + content +
        ", problems=" + problems.toString() +
        ", total problems=" + problemQuantity +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "\n}";
  }

  // Allows for simplicity when creating a new Page object
  public static Builder builder() {
    return new Builder();
  }
}
