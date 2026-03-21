create table users_relation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user1_id BIGINT,
    user2_id BIGINT,
    relation ENUM("FRIEND", "BLOCK")
);