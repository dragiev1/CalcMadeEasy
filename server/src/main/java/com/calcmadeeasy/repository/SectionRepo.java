package com.calcmadeeasy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Sections.Section;

@Repository
public interface SectionRepo extends JpaRepository<Section, UUID> {
  
}
