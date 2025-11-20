package com.calcmadeeasy.dto.Pages;

import com.calcmadeeasy.models.Pages.Page;

// Inbound only.
public class CreatePageDTO {
  private String content;

  // No-args constructor for Jackson.
  public CreatePageDTO() {}

  public CreatePageDTO(Page page) {
    this.content = page.getContent();
  }

  public String getContent() {
    return content;
  }

  // No setters.
}
