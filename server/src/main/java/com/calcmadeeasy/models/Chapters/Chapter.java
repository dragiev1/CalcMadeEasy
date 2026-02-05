package com.calcmadeeasy.models.Chapters;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.models.Sections.Section;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
  @JoinColumn(name = "course_id", nullable = false)
  @JsonBackReference
  private Course course;

  @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Section> sections = new ArrayList<>();

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  protected Chapter() {
    this.sections = new ArrayList<>();
  }

  public static class Builder {
    private String description;
    private String title;
    private List<Section> sections;

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

  public Course getCourse() {
    return course;
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
  }

  public void setTitle(String newTitle) {
    this.title = newTitle;
  }

  // Add one section to the sections list.
  public void addSection(Section section) {
    if (section == null)
      throw new IllegalArgumentException("Cannot add a null section");
    if (sections == null)
      sections = new ArrayList<>();
    sections.add(section);
    section.setChapter(this);
  }

  public void setCourse(Course course) {
    this.course = course;
    if (course != null && !course.getChapters().contains(this)) {
      course.getChapters().add(this);
    }
  }

  // Removers
  public void removeSectionById(UUID id) {
    Section section = sections.stream()
      .filter(s -> s.getId().equals(id))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Section was not found"));
    
      sections.remove(section);
  }

  // Helper Methods

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Chapter))
      return false;
    Chapter that = (Chapter) o;
    return id != null && id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
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
