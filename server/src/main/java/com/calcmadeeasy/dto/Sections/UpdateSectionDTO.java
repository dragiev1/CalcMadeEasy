package com.calcmadeeasy.dto.Sections;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// Inbound only.
@Getter
@Setter
@NoArgsConstructor
public class UpdateSectionDTO {
  @NotBlank(message = "Section title cannot be empty.")
  private String title;
  private String description;
  @NotNull(message = "Chapter ID for a given section cannot be null.")
  private UUID chapterId;  
  @NotBlank(message = "ID must not be blank.")
  private UUID id;
  private List<PageOrderDTO> pages;

  public static class PageOrderDTO {
    private UUID pageId;
    private Integer position;

    public UUID getPageId() {
      return pageId;
    }

    public Integer getPosition() {
      return position;
    }

    public void setPageId(UUID pageId) {
      this.pageId = pageId;
    }

    public void setPosition(Integer position) {
      this.position = position;
    }
  }
}
