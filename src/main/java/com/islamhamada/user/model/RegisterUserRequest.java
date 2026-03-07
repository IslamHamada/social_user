package com.islamhamada.user.model;

import lombok.Data;

@Data
public class RegisterUserRequest {
    String auth0Id;
    String username;
    String email;
}
