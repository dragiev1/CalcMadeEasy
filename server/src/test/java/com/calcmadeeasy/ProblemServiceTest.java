package com.calcmadeeasy;

import com.calcmadeeasy.models.Problem.Problem;
import com.calcmadeeasy.models.Problem.ProblemType;
import com.calcmadeeasy.models.Problem.ProblemSolutionType;
import com.calcmadeeasy.services.ProblemServices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

import jakarta.transaction.Transactional;

@SpringBootApplication
@Transactional
@ActiveProfiles("test")
public class ProblemServiceTest {
  @Autowired
  private ProblemServices problemService;

  @Test
  public void testGetAllProblems() {
    
  }
}
