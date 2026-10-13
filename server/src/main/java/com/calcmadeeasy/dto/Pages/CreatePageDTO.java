package com.calcmadeeasy.dto.Pages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Inbound only.
@Getter
@Setter
@NoArgsConstructor
public class CreatePageDTO {
  private String content;
  @NotBlank(message = "Position must not be blank.")
  @Positive(message = "Position must be positive.")
  private Integer position;
}
