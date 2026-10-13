package com.calcmadeeasy.dto.Tags;


import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Inbound only.
@Setter
@Getter
@NoArgsConstructor
public class CreateTagDTO {
  @NotBlank(message = "Tag name cannot be empty.")
  private String tagName;
  @NotNull(message = "Difficulty cannot be null.")
  @DecimalMin(value = "0.0", message = "Difficulty must be at least 0.")
  @DecimalMax(value = "1.0", message = "Difficulty must not exceed 1.")
  private BigDecimal difficulty;
}
