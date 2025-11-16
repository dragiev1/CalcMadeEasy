package com.calcmadeeasy.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calcmadeeasy.dto.Problems.CreateProblemDTO;
import com.calcmadeeasy.dto.Problems.ProblemDTO;
import com.calcmadeeasy.dto.Problems.ProblemResponseDTO;
import com.calcmadeeasy.services.ProblemServices;

@RestController
@RequestMapping("/api/v1/problems")
public class ProblemController {
  private ProblemServices problemService;

  // No args constructor for Jackson.
  public ProblemController() {
  }

  public ProblemController(ProblemServices problemService) {
    this.problemService = problemService;
  }

  // ---------------- CREATE ----------------

  @PostMapping
  public ResponseEntity<ProblemResponseDTO> createProblem(@RequestBody CreateProblemDTO problem) {
    ProblemResponseDTO t = problemService.createProblem(problem);
    return ResponseEntity.ok(t);
  }

  // ---------------- READ ----------------

  @GetMapping
  public ResponseEntity<List<ProblemDTO>> getAllProblems() {
    return ResponseEntity.ok(problemService.getAllProblemDTOs());
  }

  @GetMapping("/{problemId}")
  public ResponseEntity<ProblemDTO> getProblem(@PathVariable UUID problemId) {
    ProblemDTO p = problemService.getProblemDTO(problemId);
    return ResponseEntity.ok(p);
  }

  // ---------------- UPDATE ----------------

  @PutMapping("/{problemId}")
  public ResponseEntity<ProblemDTO> updateProblem(
    @PathVariable UUID problemId, 
    @RequestBody CreateProblemDTO request) {
      ProblemDTO p = problemService.updateProblem(problemId, request);
      return ResponseEntity.ok(p);
  }

}
