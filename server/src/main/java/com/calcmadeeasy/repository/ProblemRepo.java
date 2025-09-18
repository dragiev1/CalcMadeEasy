package com.calcmadeeasy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calcmadeeasy.models.Problem.Problem;

public interface ProblemRepo extends JpaRepository<Problem, UUID> {

}
