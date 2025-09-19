package com.calcmadeeasy.models.Problem;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Problem {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String description;
  private int points;
  private boolean isChallenge;
  private String solution;

  // NUMERICAL or EXPRESSION
  @Enumerated(EnumType.STRING)
  private ProblemSolutionType solutionType;

  @ElementCollection
  private List<String> topics;

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  // No-argument constructor for JPA.
  public Problem() {
    this.topics = new ArrayList<>();
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }

  // Builder inner class
  public static class Builder {
    private String description;
    private String solution;
    private ProblemSolutionType solutionType;
    private boolean isChallenge;
    private List<String> topics;
    private int points;
    private Instant createdAt;

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder solution(String solution) {
      this.solution = solution;
      return this;
    }

    public Builder solutionType(ProblemSolutionType solutionType) {
      this.solutionType = solutionType;
      return this;
    }

    public Builder isChallenge(boolean isChallenge) {
      this.isChallenge = isChallenge;
      return this;
    }

    public Builder topics(String... topics) {
      this.topics = new ArrayList<>(List.of(topics));
      return this;
    }

    public Builder points(int points) {
      this.points = points;
      return this;
    }

    public Builder createdAt(Instant createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Problem build() {
      return new Problem(this);
    }
  }

  private Problem(Builder b) {
    this.description = b.description;
    this.solution = b.solution;
    this.solutionType = b.solutionType;
    this.isChallenge = b.isChallenge;
    this.topics = b.topics == null ? new ArrayList<>() : new ArrayList<>(b.topics);
    this.points = b.points;
    this.createdAt = b.createdAt == null ? Instant.now() : b.createdAt;
    this.updatedAt = this.createdAt;
  }

  // Getters
  public UUID getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public String getSolution() {
    return solution;
  }

  public ProblemSolutionType getSolutionType() {
    return solutionType;
  }

  public boolean getIsChallenge() {
    return isChallenge;
  }

  public List<String> getTopics() {
    return topics;
  }

  public int getPoints() {
    return points;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void touch() {
    this.updatedAt = Instant.now();
  }

  // Setters

  public void setDescription(String newDescription) {
    this.description = Objects.requireNonNull(newDescription);
    touch();
  }

  public void setSolution(String solution) {
    this.solution = solution;
    touch();
  }

  public void setIsChallenge(boolean trueOrFalse) {
    this.isChallenge = trueOrFalse;
    touch();
  }

  public void setPoints(int newPoints) {
    if (newPoints < 0)
      throw new IllegalArgumentException("Points cannot be negative!");
    this.points = newPoints;
    touch();
  }

  public void setTopicsList(List<String> newTopics) {
    this.topics = new ArrayList<String>(Objects.requireNonNull(newTopics));
    touch();
  }

  public void setSolutionType(ProblemSolutionType newSolutionType) {
    if(newSolutionType != ProblemSolutionType.NUMERICAL && newSolutionType != ProblemSolutionType.EXPRESSION) {
      System.err.println("ERROR: Can only assign NUMERICAL or EXPRESSION solution types.");
      return;
    }
    this.solutionType = Objects.requireNonNull(newSolutionType);
    touch();
  }

  public void setTopic(String newTopic) {
    if(newTopic.equals("") || newTopic.equals(" ")) {
      System.err.println("Error: Cannot input empty string.");
      return;
    }
    this.topics.add(newTopic);
    touch();
  }

  // Removers
  public void removeTopic(String topic) {
    if (topic.isEmpty())
      System.out.println("Empty topic inputted");

    if (this.topics.contains(topic)) {
      this.topics.remove(topic);
      touch();
      return;
    } else
      System.out.println(topic + " not found in list.");
  }

  @Override
  public String toString() {
    return "\nProblem{\n" +
        "id=" + id +
        ", description=" + description +
        ", solution=" + solution +
        ", solutionType=" + solutionType +
        ", isChallenge=" + isChallenge +
        ", points=" + points +
        ", topics=" + topics +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "\n}";
  }

  // Allows for simplicity when creating a new Problem object
  public static Builder builder() {
    return new Builder();
  }
}
