package com.calcmadeeasy.models.Sections;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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

@Entity
public class Section {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String description;
  private String title;

  @ManyToOne(fetch = FetchType.LAZY) // Many Sections in a one chapter.
  @JoinColumn(name = "chapter_id", nullable = false)
  @JsonBackReference
  private Chapter chapter;

  @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Page> pages;

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  // No-argument constructor for JPA
  protected Section() {
    this.pages = new ArrayList<>();
  }

  public static class Builder {
    private String description;
    private String title;
    private List<Page> pages;

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder pages(Page... pages) {
      this.pages = new ArrayList<>(Arrays.asList(pages));
      return this;
    }

    public Section build() {
      Section s = new Section(this);
      if (pages != null) {
        for (Page p : pages)
          p.setSection(s);
      }
      return s;
    }
  }

  private Section(Builder b) {
    this.description = b.description;
    this.title = b.title;
    this.pages = b.pages == null ? new ArrayList<>() : new ArrayList<>(b.pages);
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

  public int getPageQuantity() {
    return pages.size();
  }

  public List<Page> getPages() {
    return pages;
  }

  public UUID getChapterId() {
    return chapter.getId();
  }

  public Chapter getChapter() {
    return chapter;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  // Setters

  public void setDescription(String newDescription) {
    this.description = newDescription;
  }

  public void setTitle(String newTitle) {
    this.title = newTitle;
  }

  public void addPage(Page newPage) {
    pages.add(Objects.requireNonNull(newPage));
  }

  public void setChapter(Chapter chapter) {
    this.chapter = chapter;
    if (chapter != null && !chapter.getSections().contains(this))
      chapter.getSections().add(this);
  }

  // Removers

  public void removePage(UUID pageId) {
    Page page = pages.stream()
        .filter(p -> p.getId().equals(pageId))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Page not found"));

    pages.remove(page);
  }

  // Helper Method

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Section))
      return false;
    Section that = (Section) o;
    return id != null && id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "\nSection{" +
        "id=" + id +
        ", description=" + description +
        ", title=" + title +
        ", pageQuantity=" + getPageQuantity() +
        ", updatedAt=" + updatedAt +
        ", createdAt=" + createdAt +
        '}';
  }

  // Allows for simplicity when creating a new Section object
  public static Builder builder() {
    return new Builder();
  }
}
