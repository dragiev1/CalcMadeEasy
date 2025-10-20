package com.calcmadeeasy.models.Tags;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.calcmadeeasy.models.Problem.Problem;

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

  @Column(unique = true, nullable = false)
  private String name;
  private double difficulty; // Scale of 0 to 1 for each tag. 0 = simple and 1 = hard.

  @ManyToMany(mappedBy = "tags")
  private Set<Problem> problems = new HashSet<>();

  // No-args constructor for JPA.
  public Tag() {
  }

  public Tag(String name, double difficulty) {
    this.name = name;
    this.difficulty = difficulty;
  }

  // Getters

  public UUID getId() {
    return id;
  }

  public String getTagName() {
    return name;
  }

  public double getDifficulty() {
    return difficulty;
  }

  // Setters

  public void setTagName(String name) {
    if (name.equals("") || name.equals(" "))
      throw new IllegalArgumentException("ERROR: Cannot make empty tag");
    this.name = name;
  }

  public void setDifficulty(double difficulty) {
    if (difficulty < 0 || difficulty > 1) {
      throw new IllegalArgumentException("ERROR: Difficulty must be between 0 and 1");
    }
    this.difficulty = difficulty;
  }

  @Override
  public String toString() {
    return "\nTag{\n" +
        "id=" + id +
        ", tagName=" + name +
        ", difficulty=" + difficulty +
        "\n}";
  }
}
