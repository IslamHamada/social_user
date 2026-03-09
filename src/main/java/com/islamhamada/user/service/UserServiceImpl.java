package com.islamhamada.user.service;

import com.islamhamada.social.dto.UserDTO;
import com.islamhamada.user.entity.User;
import com.islamhamada.user.model.RegisterUserRequest;
import com.islamhamada.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTOs = users.stream()
                .map(user -> UserDTO.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build())
                .toList();
        return usersDTOs;
    }

    @Override
    public void registerUser(RegisterUserRequest request) {
        Optional<User> user = userRepository.findByAuth0Id(request.getAuth0Id());
        if(!user.isPresent()){
            User user2 = User.builder()
                            .username(request.getUsername())
                            .email(request.getEmail())
                            .auth0Id(request.getAuth0Id())
                            .build();
            userRepository.save(user2);
        }
    }
}
