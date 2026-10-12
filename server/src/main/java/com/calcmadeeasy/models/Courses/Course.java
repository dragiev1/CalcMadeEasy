package com.calcmadeeasy.models.Courses;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.calcmadeeasy.models.Chapters.Chapter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Course {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @EqualsAndHashCode.Include
  @NotBlank(message = "Title cannot be empty.")
  private String title;

  private String description;

  @Builder.Default
  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true) // Many chapters in one course.
  @JsonManagedReference
  private List<Chapter> chapters = new ArrayList<>();

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  // Getters

  public Chapter getChapterById(UUID chapterId) {
    return chapters.stream()
        .filter(c -> c.getId().equals(chapterId))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Chapter not found within Course object"));
  }

  public int getChapterQuantity() {
    return this.chapters == null ? 0 : chapters.size();
  }

  // Setters

  public void addChapter(Chapter chapter) {
    Objects.requireNonNull(chapter, "Chapter cannot be null");
    if(chapter.getCourse() == this) return;
    this.chapters.add(chapter);
    chapter.setCourse(this);
  }

  public void removeChapterById(UUID chapterId) {
    this.chapters.removeIf(chapter -> {
      if(chapter.getId().equals(chapterId)) {
        chapter.setCourse(null);
        return true;
      }
      return false;
    });
  }
}
