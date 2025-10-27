package com.calcmadeeasy.models.Chapters;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.calcmadeeasy.models.Courses.Course;
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
public class Chapter {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String description;
  private String title;

  @ManyToOne(fetch = FetchType.LAZY) // Many Chapters in a one course.
  @JoinColumn(name = "course_id", nullable = true)
  private Course course;

  @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
  private List<Section> sections;

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  public Chapter() {
    this.sections = new ArrayList<>();
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }

  public static class Builder {
    private String description;
    private String title;
    private List<Section> sections;
    private Instant createdAt;

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder sections(Section... sections) {
      this.sections = new ArrayList<>(Arrays.asList(sections));
      return this;
    }

    public Builder createdAt(Instant createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Chapter build() {
      Chapter c = new Chapter(this);
      if (c.sections != null) {
        for (Section s : c.sections)
          s.setChapter(c);
      }
      return c;
    }
  }

  private Chapter(Builder b) {
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

  // Can either add one or more sections as once.
  public void setSections(List<Section> newSection) {
    if (newSection == null || sections.isEmpty())
      System.out.println("Cannot add a null or empty section");
    else
      for (Section s : newSection)
        this.sections.add(s);
  }

  // Fully replaces section list with new sections.
  public void setSectionList(Section... newSections) {
    this.sections = new ArrayList<Section>(); // Wipes out old data
    for (Section s : newSections)
      this.sections.add(s);
  }

  public void setCourse(Course course) {
    this.course = course;
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
    return "\nChapter{\n" +
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
