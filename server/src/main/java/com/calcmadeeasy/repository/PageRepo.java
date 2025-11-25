package com.calcmadeeasy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Pages.Page;
import com.calcmadeeasy.models.Sections.Section;

@Repository
public interface PageRepo extends JpaRepository<Page, UUID> {

  List<Page> findBySectionId(UUID sectionId);

  @Query("SELECT MAX(p.position) FROM Page p WHERE p.section = :section")
  Integer findMaxPositionBySection(@Param("section") Section section);

}
