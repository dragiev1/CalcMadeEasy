package server.models.Chapters;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import server.models.Sections.Section;

public class Chapter {
  private final UUID id;
  private String description;
  private String title;
  private List<Section> sections;
  private Instant createdAt;
  private Instant updatedAt;

  public static class Builder {
    private UUID id;
    private String description;
    private String title;
    private List<Section> sections;
    private Instant createdAt;

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder sections(List<Section> sections) {
      this.sections = sections;
      return this;
    }

    public Builder createdAt(Instant createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Chapter build() {
      return new Chapter(this);
    }
  }

  private Chapter(Builder b) {
    this.id = b.id == null ? UUID.randomUUID() : b.id;
    this.description = b.description;
    this.title = b.title;
    this.sections = b.sections == null ? new ArrayList<Section>() : new ArrayList<Section>(b.sections);
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

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public List<Section> getSections() {
    return sections;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  // Setters
  public void setDescription(String newDescription) {
    this.description = newDescription;
    touch();
  }

  public void setTitle(String newTitle) {
    this.title = newTitle;
    touch();
  }

  public void setNewSection(Section newSection) {
    if (newSection == null)
      System.out.println("Cannot add a null section");
    else
      this.sections.add(newSection);
  }

  public void setNewSectionList(Section... newSections) {
    this.sections = new ArrayList<Section>();  // Wipe out old data
    for (Section s : newSections)
      this.sections.add(s);
  }

  // Removers
  public void removeSectionById(UUID id) {
    for (Section s : this.sections) {
      if (s.getId() == id) {
        this.sections.remove(s);
        System.out.println("Removed " + s + " from section list");
      } else
        System.out.println("No section was found in the list");
      return;
    }
  }

  public String toString() {
    return "Page{\n" +
        "id=" + id +
        ", description=" + description +
        ", sectionsQuantity=" + sections.size() +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "\n}";
  }

  // Allows for simplicity when creating a new Chapter object
  public static Builder builder() {
    return new Builder();
  }
}
