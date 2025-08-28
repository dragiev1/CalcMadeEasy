package server.models.Sections;

import java.util.List;
import java.util.UUID;

import server.models.Pages.Page;

public class Section {
  private final UUID id;
  private String description;
  private String title;
  private int problemQuantity;
  private float progress;
  private int numOfPages;
  private List<Page> pages;
}
