package com.calcmadeeasy.dto.Pages;


// Inbound only.
public class CreatePageDTO {
  private String content;
  private Integer position;

  // No-args constructor for Jackson.
  public CreatePageDTO() {}

  // Getters

  public String getContent() {
    return content;
  }

  public Integer getPosition() {
    return position;
  }

  // Setters (Optional)

  public void setContent(String content) {
    this.content = content;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }
}
