package com.islamhamada.user.controller;

import com.islamhamada.social.dto.UserDTO;
import com.islamhamada.user.model.RegisterUserRequest;
import com.islamhamada.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }

    @PostMapping
    public void registerUser(@RequestBody RegisterUserRequest request){
        userService.registerUser(request);
    }
}
