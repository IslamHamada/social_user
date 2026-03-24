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

    @Override
    public String getRelation(String username1, String username2) {
        User user1 = userRepository.findByUsername(username1).orElseThrow();
        User user2 = userRepository.findByUsername(username2).orElseThrow();
        Optional<UsersRelation> usersRelation = usersRelationRepository.findByUser1AndUser2(user1, user2);
        if(usersRelation.isEmpty())
            return "STRANGER";
        else
            return usersRelation.get().getRelation() + "";
    }

    @Override
    public void sendFriendRequest(String username1, String username2) {
        User user1 = userRepository.findByUsername(username1).orElseThrow();
        User user2 = userRepository.findByUsername(username2).orElseThrow();
        UsersRelation relation = UsersRelation.builder()
                .user1(user1)
                .user2(user2)
                .relation(Relation.SENDING_FRIENDSHIP_REQUEST)
                .build();
        usersRelationRepository.save(relation);
        UsersRelation relation2 = UsersRelation.builder()
                .user1(user2)
                .user2(user1)
                .relation(Relation.RECEIVING_FRIENDSHIP_REQUEST)
                .build();
        usersRelationRepository.save(relation2);
    }

    @Override
    public void cancelFriendRequest(String username1, String username2) {
        User user1 = userRepository.findByUsername(username1).orElseThrow();
        User user2 = userRepository.findByUsername(username2).orElseThrow();
        UsersRelation relation1 = usersRelationRepository.findByUser1AndUser2(user1, user2).orElseThrow();
        UsersRelation relation2 = usersRelationRepository.findByUser1AndUser2(user2, user1).orElseThrow();
        usersRelationRepository.deleteById(relation1.getId());
        usersRelationRepository.deleteById(relation2.getId());
    }

    @Override
    public void acceptFriendRequest(String username1, String username2) {
        User user1 = userRepository.findByUsername(username1).orElseThrow();
        User user2 = userRepository.findByUsername(username2).orElseThrow();
        UsersRelation relation1 = usersRelationRepository.findByUser1AndUser2(user1, user2).orElseThrow();
        UsersRelation relation2 = usersRelationRepository.findByUser1AndUser2(user2, user1).orElseThrow();
        relation1.setRelation(Relation.FRIEND);
        relation2.setRelation(Relation.FRIEND);
        usersRelationRepository.save(relation1);
        usersRelationRepository.save(relation2);
    }

    @Override
    public List<UserDTO> getFriends(String username) {
        User user1 = userRepository.findByUsername(username).orElseThrow();
        List<UsersRelation> relations = usersRelationRepository.findByUser1AndRelation(user1, Relation.FRIEND);
        List<User> users = relations.stream().map(r -> r.getUser2()).toList();
        List<UserDTO> userDTOS = users.stream()
                .map(user -> UserDTO.builder()
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .build()).toList();
        return userDTOS;
    }

    @Override
    public List<UserDTO> searchByKeyword(String keyword) {
        List<User> users = userRepository.findByKeyword("%" + keyword + "%");
        List<UserDTO> userDTOS = users.stream()
                .map(user -> UserDTO.builder()
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .build())
                .toList();
        return userDTOS;
    }

    @Override
    public byte[] getPic(String username) {
        User user =  userRepository.findByUsername(username).orElseThrow();
        return user.getPic();
    }

    @Override
    public byte[] changePic(String username, MultipartFile pic) throws IOException {
        User user = userRepository.findByUsername(username).orElseThrow();
        user.setPic(pic.getBytes());
        return userRepository.save(user).getPic();
    }
}
