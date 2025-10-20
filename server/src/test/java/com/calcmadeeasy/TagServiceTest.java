package com.calcmadeeasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import com.calcmadeeasy.repository.TagRepo;
import com.calcmadeeasy.services.TagServices;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class TagServiceTest {
    
  @Autowired
  private TagServices tagServices;

  // Create

  


} 
