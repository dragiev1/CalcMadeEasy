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
  @JoinColumn(name = "chapter_id")
  private Chapter chapter;

  @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
  private List<Page> pages;

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  // No-argument constructor for JPA
  public Section() {
    this.pages = new ArrayList<>();
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }

  public static class Builder {
    private String description;
    private String title;
    private List<Page> pages;
    private Instant createdAt;

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

    public Builder createdAt(Instant createdAt) {
      this.createdAt = createdAt;
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
    return chapter != null ? chapter.getId() : null;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void touch() {
    this.updatedAt = Instant.now();
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

  public void setPage(Page newPage) {
    this.pages.add(Objects.requireNonNull(newPage));
    touch();
  }

  public void setPageList(Page... newPages) {
    this.pages = new ArrayList<Page>();
    for (Page p : newPages)
      this.pages.add(p);
    touch();
  }

  public void setChapter(Chapter chapter) {
    this.chapter = chapter;
  }

  // Removers

  public void removePageById(UUID id) {
    if (pages.isEmpty() || pages == null)
      System.out.println("No pages in this section.");
    for (Page p : this.pages) {
      if (p.getId().equals(id)) {
        pages.remove(p);
        System.out.println("Removed " + p.toString() + "\nfrom pages list");
        return;
      }
    }
    touch();
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
        ", pageQuantity=" + pages.size() +
        ", updatedAt=" + updatedAt +
        ", createdAt=" + createdAt +
        '}';
  }

  // Allows for simplicity when creating a new Section object
  public static Builder builder() {
    return new Builder();
  }
}
