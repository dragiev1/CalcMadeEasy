package server.src.models.Courses;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import server.src.models.Chapters.Chapter;

public class Course {
  private final UUID id;
  private String title;
  private String description;
  private List<Chapter> chapters;
  private Instant createdAt;
  private Instant updatedAt;

  public static class Builder {
    private UUID id;
    private String title;
    private String description;
    private List<Chapter> chapters = new ArrayList<>();
    private Instant createdAt;

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder chapters(Chapter... chapters) {
      this.chapters = Arrays.asList(chapters);
      return this;
    }

    public Builder createdAt(Instant createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Course build() {
      return new Course(this);
    }
  }

  private Course(Builder b) {
    this.id = b.id == null ? UUID.randomUUID() : b.id;
    this.title = b.title;
    this.description = b.description;
    this.chapters = b.chapters == null ? new ArrayList<>() : new ArrayList<>(b.chapters);
    this.createdAt = b.createdAt == null ? Instant.now() : b.createdAt;
    this.updatedAt = this.createdAt;
  }

  public UUID getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public List<Chapter> getChapters() {
    return chapters;
  }

  public int getChapterQuantity() {
    return chapters.size();
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  // Setters

  public void touch() {
    this.updatedAt = Instant.now();
  }

  public void setTitle(String newTitle) {
    this.title = newTitle;
    touch();
  }

  public void setDescription(String newDescrip) {
    this.description = newDescrip;
    touch();
  }

  public void setChapters(Chapter... chapters) {
    if (chapters.length == 0 || chapters == null) {
      System.out.println("Cannot add null or empty chapters.");
      return;
    }
    for (Chapter c : chapters)
      this.chapters.add(c);
    System.out.println("Added chapter(s) succesfully");
  }

  // Removers
  public void removeChapterById(UUID id) {
    for (Chapter c : chapters) {
      if (c.getId() == id) {
        this.chapters.remove(c);
        System.out.println("Removed " + c.toString() + " from Chapters list");
        return;
      }
    }
    System.out.println("Could not find chapter");

  }

  public String toString() {
    return "\nChapter{\n" +
        "id=" + id +
        ", description=" + description +
        ", title=" + title +
        ", chaptersQuantity=" + chapters.size() +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "\n}"; 
  }

  // Allows for simplicity when creating a new Course object
  public static Builder builder() {
    return new Builder();
  }
}
