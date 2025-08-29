package server.models.Sections;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import server.models.Pages.Page;

public class Section {
  private final UUID id;
  private String description;
  private String title;
  private float progress;
  private int numOfPages;
  private List<Page> pages;
  private Instant createdAt;
  private Instant updatedAt;

  public class Builder {
    private UUID id;
    private String description;
    private String title;
    private int problemQuantity;
    private float progress;
    private int numOfPages;
    private List<Page> pages;
    private Instant updatedAt;
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

    public Builder problemQuantity(int problemQuantity) {
      this.problemQuantity = problemQuantity;
      return this;
    }

    public Builder progress(float progress) {
      this.progress = progress;
      return this;
    }

    public Builder numOfPages(int numOfPages) {
      this.numOfPages = numOfPages;
      return this;
    }

    public Builder pages(List<Page> pages) {
      this.pages = pages;
      return this;
    }

    public Builder createdAt(Instant createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder updatedAt(Instant updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public Section build() {
      return new Section(this);
    }
  }

  private Section(Builder b) {
    this.id = b.id == null ? UUID.randomUUID() : b.id;
    this.description = b.description;
    this.title = b.title;
    this.progress = b.progress;
    this.pages = b.pages == null ? new ArrayList<>() : new ArrayList<>(b.pages);
    this.numOfPages = b.pages.size();
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

  public float getProgress() {
    return progress;
  }

  public List<Page> getPages() {
    return pages;
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

  public void updateDescription(String newDescription) {
    this.description = newDescription;
    touch();
  }

  public void updateProgress(float newProgress) {
    this.progress = newProgress;
    touch();
  }

  public void updateTitle(String newTitle) {
    this.title = newTitle;
    touch();
  }

  public void updatePageQuantity(int newPageQuantity) {
    this.numOfPages += newPageQuantity;
    touch();
  }

  public void addPage(Page newPage) {
    this.pages.add(Objects.requireNonNull(newPage));
    touch();
  }

  public void replacePages(List<Page> newPages) {
    this.pages = new ArrayList<Page>(Objects.requireNonNull(newPages));
    touch();
  }

  public void removePageById(UUID id) {
    if (pages.isEmpty() || pages == null)
      System.out.println("No pages in this section");
    for (Page p : pages) {
      if (p.getId().equals(id)) {
        pages.remove(p);
        System.out.println("Removed " + p.toString() + "\nfrom pages list");
      }
    }
  }

}
