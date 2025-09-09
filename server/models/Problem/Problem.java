package server.models.Problem;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Problem {
  private final UUID id;
  private String description;
  private ProblemSolutionType type;
  private String solutionPath;
  private boolean isChallenge;
  private List<String> topics;
  private int points;
  private Instant createdAt;
  private Instant updatedAt;

  // Builder inner class
  public static class Builder {
    private UUID id;
    private String description;
    private ProblemSolutionType type;
    private String solutionPath;
    private boolean isChallenge;
    private List<String> topics;
    private int points;
    private Instant createdAt;

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder type(ProblemSolutionType type) {
      this.type = type;
      return this;
    }

    public Builder solutionPath(String solutionPath) {
      this.solutionPath = solutionPath;
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
    this.id = b.id == null ? UUID.randomUUID() : b.id;
    this.description = b.description;
    this.type = b.type;
    this.solutionPath = b.solutionPath;
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

  public ProblemSolutionType getType() {
    return type;
  }

  public String getSolutionPath() {
    return solutionPath;
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

  public void setType(ProblemSolutionType newType) {
    this.type = Objects.requireNonNull(newType);
    touch();
  }

  public void setDifficulty(boolean trueOrFalse) {
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

  public void addTopic(String newTopic) {
    this.topics.add(newTopic);
  }

  public void setSolutionPath(String newPath) {
    this.solutionPath = Objects.requireNonNull(newPath);
    touch();
  }

  // Removers
  public void removeTopic(UUID problemId, String topic) {
    if (topic.isEmpty())
      System.out.println("Empty topic inputted");

    if (this.topics.contains(topic)) {
      this.topics.remove(topic);
      return;
    } else
      System.out.println(topic + " not found in list.");
  }

  @Override
  public String toString() {
    return "\nProblem{\n" +
        "id=" + id +
        ", description=" + description +
        ", solutionType=" + type +
        ", solutionPath=" + solutionPath +
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
