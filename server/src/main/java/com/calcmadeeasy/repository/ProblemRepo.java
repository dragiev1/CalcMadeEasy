package com.calcmadeeasy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Problem.Problem;

@Repository
public interface ProblemRepo extends JpaRepository<Problem, UUID> {

}
