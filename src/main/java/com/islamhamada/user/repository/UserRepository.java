package com.islamhamada.user.repository;

import com.islamhamada.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByAuth0Id(String auth0Id);
}
