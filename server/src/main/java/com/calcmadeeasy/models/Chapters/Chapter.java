package com.calcmadeeasy.models.Chapters;

import java.time.Instant;
import java.util.ArrayList;
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
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Chapter {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String description;

  @EqualsAndHashCode.Include
  @NotBlank(message = "Title cannot be blank.")
  private String title;

  @ManyToOne(fetch = FetchType.LAZY) // Many Chapters in a one course.
  @JoinColumn(name = "course_id", nullable = false)
  @JsonBackReference
  private Course course;

  @Builder.Default
  @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Section> sections = new ArrayList<>();

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  // Getters

  public int getSectionQuantity() {
    return this.sections == null ? 0 :sections.size();
  }

  // Setters
  
  public void addSection(Section section) {
    Objects.requireNonNull(section, "Section cannot be null.");
    if (section.getChapter() == this) return;
    this.sections.add(section);
    section.setChapter(this);
  }

  public void setCourse(Course course) {
    this.course = course;
    if (course != null && !course.getChapters().contains(this)) {
      course.getChapters().add(this);
    }
  }

  // Removers

  public void removeSection(UUID id) {
    this.sections.removeIf(section -> {
      if(section.getId().equals(id)) {
        section.setChapter(null);  // Breaks link so Hibernate deletes orphan.
        return true;
      }
      return false;
    });
  }
}
