package server.models.Pages;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import server.models.Problem.Problem;

public class Page {
  private final UUID id;
  private String content;  // Latex markdown text
  private List<Problem> exercises;
  private List<Problem> homework;
  private Instant createdAt;
  private Instant updatedAt;

  public static class Builder {
    private UUID id;
    private String content;
    private List<Problem> exercises;
    private List<Problem> homework;
    private Instant createdAt;
    private Instant updatedAt;

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

    public Builder content(String content) {
      this.content = content;
      return this;
    }

    public Builder exercises(List<Problem> exercises) {
      this.exercises = exercises;
      return this;
    }

    public Builder homework(List<Problem> homework) {
      this.homework = homework;
      return this;
    }

    public Builder createdAt(Instant createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder updatedAt(Instant updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public Page build() {
      return new Page(this);
    }
  }

  private Page(Builder b) {
    this.id = b.id == null ? UUID.randomUUID() : b.id;
    this.content = b.content;
    this.exercises = b.exercises == null ? new ArrayList<>() : new ArrayList<>(b.exercises);
    this.homework = b.homework == null ? new ArrayList<>() : new ArrayList<>(b.homework);
    this.createdAt = b.createdAt;
    this.updatedAt = b.updatedAt;
  }

  // Getters
  public UUID getId() {
    return id;
  }

  // Returns a LaTeX markdown text as a string
  public String getContent() {
    return content;
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
  public void updateContent(String newContent) {
    this.content = newContent;
  }

  public void addNewExercise(Problem newExercise) {
    this.exercises.add(newExercise);
  }

  public void replaceExerciseList(List<Problem> newExercises) {
    this.exercises = new ArrayList<Problem>(Objects.requireNonNull(newExercises));
  }

  public void touch() {
    this.updatedAt = Instant.now();
  }
}
