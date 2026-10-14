package com.calcmadeeasy.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Problems.Problem;

@Repository
public interface ProblemRepo extends JpaRepository<Problem, UUID> {
  
  @Query("""
    SELECT p
    FROM Problem p
    JOIN PageProblem pp ON pp.problem.id = p.id
    WHERE pp.page.id = :pageId
  """)
  List<Problem> findByProblemsByPageId(@Param("pageId") UUID pageId);

  @Query("SELECT p FROM Problem p JOIN p.tags t WHERE t.tagName = :tagName")
  List<Problem> findByTagName(@Param("tagName") String tagName);

  @Query("SELECT p FROM Problem p LEFT JOIN FETCH p.tags WHERE p.id = :id")
  Optional<Problem> findByIdWithTags(@Param("id") UUID id);
}
