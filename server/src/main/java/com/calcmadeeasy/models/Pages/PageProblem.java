package com.calcmadeeasy.models.Pages;

import java.util.UUID;

import com.calcmadeeasy.models.Problem.Problem;
import com.calcmadeeasy.models.Problem.ProblemType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PageProblem {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "page_id")
  private Page page;

  @ManyToOne
  @JoinColumn(name = "problem_id")
  private Problem problem;

  // HOMEWORK or EXERCISE
  @Enumerated(EnumType.STRING)
  private ProblemType type;

  // No-args constructor for JPA
  public PageProblem() {
  }

  public PageProblem(Page page, Problem problem, ProblemType type) {
    this.page = page;
    this.problem = problem;
    this.type = type;
  }

  public Problem getProblem() {
    return problem;
  }

  public ProblemType getType() {
    return type;
  }
}
