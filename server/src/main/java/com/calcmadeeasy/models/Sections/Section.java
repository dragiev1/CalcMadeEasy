package com.calcmadeeasy.models.Sections;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.calcmadeeasy.models.Chapters.Chapter;
import com.calcmadeeasy.models.Pages.Page;
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
import jakarta.persistence.Table;
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
@Table(name = "sections")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Section {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String description;
  
  @NotBlank(message = "A section must have a title.")
  @EqualsAndHashCode.Include
  private String title;

  @ManyToOne(fetch = FetchType.LAZY) // Many Sections in a one chapter.
  @JoinColumn(name = "chapter_id", nullable = false)
  @JsonBackReference  // Prevents infinite JSON recursion.
  private Chapter chapter;

  @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  @Builder.Default
  private List<Page> pages = new ArrayList<>();

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  // Getters

  public int getPageQuantity() {
    return this.pages == null ? 0 : pages.size();
  }

  // Setters
 
  public void addPage(Page newPage) {
    Objects.requireNonNull(newPage, "Page cannot be null");
    this.pages.add(newPage);
    // Maintain bidirectional relationship.
    newPage.setSection(this);
  }

  public void setChapter(Chapter chapter) {
    this.chapter = chapter;
    // Maintain bidirectional relationship.
    if (chapter != null && !chapter.getSections().contains(this))
      chapter.getSections().add(this);
  }

  // Removers

  public void removePage(UUID pageId) {
    Page page = pages.stream()
        .filter(p -> p.getId().equals(pageId))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Page not found"));

    this.pages.remove(page);
    // Breaks back-reference so Hibernate knows it's been orphaned.
    page.setSection(null);
  }
}
