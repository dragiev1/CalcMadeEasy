package com.calcmadeeasy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Pages.Page;

@Repository
public interface PageRepo extends JpaRepository<Page, UUID> {

  List<Page> findBySectionId(UUID sectionId);

  int findProblemQuantity(UUID pageId);

}
