package com.calcmadeeasy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Pages.Page;

@Repository
public interface PageRepo extends JpaRepository<Page, UUID> {
  
}
