package com.calcmadeeasy.dto.Sections;

import java.util.List;
import java.util.UUID;


// Inbound only.
public class UpdateSectionDTO extends CreateSectionDTO {
  private UUID id;
  private List<PageOrderDTO> pages;

  public UpdateSectionDTO() {}

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

  public UUID getId() {
    return id;
  }

  public List<PageOrderDTO> getPages() {
    return pages;
  }

}
