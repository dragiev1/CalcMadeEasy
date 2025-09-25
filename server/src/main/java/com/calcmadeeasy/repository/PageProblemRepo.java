package com.calcmadeeasy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Pages.PageProblem;
import com.calcmadeeasy.models.Problem.Problem;

@Repository
public interface PageProblemRepo extends JpaRepository<UUID, PageProblem> {
  @Query("""
        SELECT p
        FROM Problem p
        JOIN PageProblem pp ON pp.problem.id = p.id
        WHERE pp.page.id = :pageId
      """)
  List<Problem> findByPageProblemByPageId(@Param("pageId") UUID pageId);
}
