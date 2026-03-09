package com.islamhamada.user.service;

import com.islamhamada.social.dto.UserDTO;
import com.islamhamada.user.model.RegisterUserRequest;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();
    void registerUser(RegisterUserRequest request);
}