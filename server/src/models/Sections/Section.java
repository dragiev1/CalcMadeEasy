package server.src.models.Sections;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import server.src.models.Pages.Page;

public class Section {
  private final UUID id;
  private String description;
  private String title;
  private List<Page> pages;
  private Instant createdAt;
  private Instant updatedAt;

  public static class Builder {
    private UUID id;
    private String description;
    private String title;
    private List<Page> pages;
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

    public Builder pages(Page... pages) {
      this.pages = List.of(pages);
      return this;
    }

    public Builder createdAt(Instant createdAt) {
      this.createdAt = createdAt;
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
    for (Page p : newPages) this.pages.add(p);
    touch();
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
