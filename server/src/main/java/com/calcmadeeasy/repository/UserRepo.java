package com.calcmadeeasy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calcmadeeasy.models.Users.User;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
  
}
