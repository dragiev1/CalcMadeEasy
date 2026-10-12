package com.calcmadeeasy.models.Pages;

import java.util.UUID;

import com.calcmadeeasy.models.Problems.Problem;
import com.calcmadeeasy.models.Problems.ProblemType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


// Makes the association of different problem types easier to 
// store and retrieve in a database perspective.
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PageProblem {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @NotNull(message = "Page cannot be null.")
  @JoinColumn(name = "page_id", nullable = false)
  private Page page;

  @ManyToOne
  @NotNull(message = "Problem cannot be null.")
  @JoinColumn(name = "problem_id", nullable = false)
  private Problem problem;

  // HOMEWORK or EXERCISE
  @Enumerated(EnumType.STRING)
  @NotNull(message = "Problem type cannot be null.")
  private ProblemType type;
}
