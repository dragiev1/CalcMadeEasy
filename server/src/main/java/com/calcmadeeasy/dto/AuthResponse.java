package com.calcmadeeasy.dto;

import com.calcmadeeasy.models.Users.User;

public record AuthResponse(
    String token,
    User user
) {
    // Java generates getters/setters automatically. 
}
