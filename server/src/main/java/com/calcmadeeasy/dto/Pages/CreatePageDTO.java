package com.calcmadeeasy.dto.Pages;

// Inbound only.
public class CreatePageDTO {
  private String content;

  // No args constructor for Jackson.
  public CreatePageDTO() {}

  public CreatePageDTO(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  // No setters.
}
