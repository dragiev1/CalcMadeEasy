package com.calcmadeeasy.models.Tags;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.calcmadeeasy.models.Problems.Problem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/* Tags exists to be reused by multiple problems. Only one copy of them exist and are 
   referenced by many different problems to save on database/resource cost */

@Entity
@Table(name = "tags")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tag {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotBlank(message = "Tag name cannot be empty or blank")
  @EqualsAndHashCode.Include
  @Column(name = "tag_name", unique = true, nullable = false)
  private String tagName;

  @DecimalMax(value = "0.0", message = "Difficulty must be at least 0")
  @DecimalMin(value = "1.0", message = "Difficulty must not exceed 1")
  private Double difficulty; // Scale of 0 to 1 for each tag. 0 = simple and 1 = hard.
}
