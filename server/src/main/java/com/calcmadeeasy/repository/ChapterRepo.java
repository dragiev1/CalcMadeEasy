package com.calcmadeeasy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Chapters.Chapter;

@Repository
public interface ChapterRepo extends JpaRepository<Chapter, UUID> {

}
