package server.models.Pages;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import server.models.Problem.Problem;

public class Page {
  private final UUID id;
  private String content; // Latex markdown text
  private int problemQuantity;
  private List<Problem> exercises;
  private List<Problem> homework;
  private Instant createdAt;
  private Instant updatedAt;

  public static class Builder {
    private UUID id;
    private String content;
    private List<Problem> exercises  = new ArrayList<>();
    private List<Problem> homework  = new ArrayList<>();
    private Instant createdAt;

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

    public Builder content(String content) {
      this.content = content;
      return this;
    }

    public Builder problemQuantity(int problemQuantity) {
      return this;
    }

    public Builder exercises(Problem... exercises) {
      this.exercises = Arrays.asList(exercises);
      return this;
    }

    public Builder homework(Problem... homework) {
      this.homework = Arrays.asList(homework);
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
    this.id = b.id == null ? UUID.randomUUID() : b.id;
    this.content = b.content;
    this.exercises = b.exercises;
    this.homework = b.homework;
    this.problemQuantity = b.exercises.size() + b.homework.size();
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
      T value,
      List<Problem> problems) {
    return problems.stream()
        .filter(problem -> Objects.equals(attributeExtractor.apply(problem), value))
        .collect(Collectors.toList());
  }

  /*
   * Returns a list of problems by containing a certain value.
   * 
   * @params Function<Problem, Collection<T>> attributeExtractor = lamba
   * expression that is sending an arbitrary type collection for data extraction
   * within this generic method.
   * T value = value using to search for problems to retrieve.
   */
  public <T> List<Problem> getProblemsContaining(
      Function<Problem, Collection<T>> attributeExtractor,
      T value,
      List<Problem> problems) {
    return problems.stream()
        .filter(problem -> attributeExtractor.apply(problem).contains(value))
        .collect(Collectors.toList());
  }

  public List<Problem> getExercises() {
    return exercises;
  }

  public List<Problem> getHomework() {
    return homework;
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

  public void setNewExercise(Problem newExercise) {
    this.exercises.add(newExercise);
    problemQuantity++;
    touch();
  }

  public void setNewHW(Problem newHomework) {
    this.homework.add(newHomework);
    problemQuantity++;
    touch();
  }

  // Removers

  // Removes a problem from a list provided. (eg. exercises or homeworks)
  public void removeProblem(List<Problem> problems, UUID id) {
    for (Problem p : problems) {
      if (p.getId().equals(id)) {
        problems.remove(p);
        System.out.println("Removed " + p.getId() + " from given list");
        problemQuantity--;
        return;
      }
    }
  }

  public void replaceExerciseList(List<Problem> newExercises) {
    problemQuantity -= this.exercises.size(); // Subtract old quantity of problems
    this.exercises = new ArrayList<Problem>(Objects.requireNonNull(newExercises));
    problemQuantity += this.exercises.size(); // Add new quantity of problems
    touch();
  }

  public void replaceHomeworkList(List<Problem> newHomeworks) {
    problemQuantity -= this.homework.size(); // Subtract old quantity of problems
    this.homework = new ArrayList<Problem>(Objects.requireNonNull(newHomeworks));
    problemQuantity += this.homework.size(); // Add new quantity of problems
    touch();
  }

  @Override
  public String toString() {
    return "Page{\n" +
        "id=" + id +
        ", content=" + content +
//        ", homeworks=" + homework.toString() +
//        ", exercises=" + exercises.toString() +
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
