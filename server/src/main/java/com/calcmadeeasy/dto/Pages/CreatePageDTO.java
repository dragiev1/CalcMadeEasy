package com.calcmadeeasy.dto.Pages;


// Inbound only.
public class CreatePageDTO {
  private String content;

  // No-args constructor for Jackson.
  public CreatePageDTO() {}

  // Getters

  public String getContent() {
    return content;
  }

  // Setters (Optional)

  public void setContent(String content) {
    this.content = content;
  }
}
