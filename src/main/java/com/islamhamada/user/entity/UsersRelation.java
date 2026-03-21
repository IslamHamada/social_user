package com.islamhamada.user.entity;

import com.islamhamada.user.model.Relation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "users_relation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne
    User user1;
    @ManyToOne
    User user2;
    @Enumerated(EnumType.STRING)
    Relation relation;
}