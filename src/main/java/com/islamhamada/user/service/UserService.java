package com.islamhamada.user.service;

import com.islamhamada.social.dto.UserDTO;
import com.islamhamada.user.model.RegisterUserRequest;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();
    void registerUser(RegisterUserRequest request);
    String getRelation(String username1, String username2);
    void sendFriendRequest(String username1, String username2);
    void cancelFriendRequest(String username1, String username2);
    void acceptFriendRequest(String username1, String username2);
    List<UserDTO> getFriends(String username);
    List<UserDTO> searchByKeyword(String keyword);
    byte[] getPic(String username);
    byte[] changePic(String username, MultipartFile pic) throws IOException;
}