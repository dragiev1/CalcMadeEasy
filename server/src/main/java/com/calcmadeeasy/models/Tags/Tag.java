package com.calcmadeeasy.models.Tags;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.calcmadeeasy.models.Problems.Problem;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "tag_name", unique = true, nullable = false)
  private String tagName;
  private Double difficulty; // Scale of 0 to 1 for each tag. 0 = simple and 1 = hard.

  @ManyToMany(mappedBy = "tags")
  private Set<Problem> problems = new HashSet<>();

  // No-args constructor for JPA.
  protected Tag() {}

  public Tag(String tagName, Double difficulty) {
    this.tagName = tagName;
    this.difficulty = difficulty;
  }

  // Getters

  public UUID getId() {
    return id;
  }

  public String getTagName() {
    return tagName;
  }

  public Double getDifficulty() {
    return difficulty;
  }

  // Setters

  public void setTagName(String tagName) {
    if (tagName.equals("") || tagName.equals(" "))
      throw new IllegalArgumentException("ERROR: Cannot make an empty tag");
    this.tagName = tagName;
  }

  public void setDifficulty(Double difficulty) {
    if (difficulty < 0 || difficulty > 1) {
      throw new IllegalArgumentException("ERROR: Difficulty must be between 0 and 1");
    }
    this.difficulty = difficulty;
  }

  // Helper Methods

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Tag))
      return false;
    Tag tag = (Tag) o;
    return Objects.equals(id, tag.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "\nTag{\n" +
        "id=" + id +
        ", tagName=" + tagName +
        ", difficulty=" + difficulty +
        "\n}";
  }
}
