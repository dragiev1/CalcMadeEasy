package com.calcmadeeasy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Tags.Tag;
import java.util.Optional;

@Repository
public interface TagRepo extends JpaRepository<Tag, UUID> {
  Optional<Tag> findByTag(String tag);
}
