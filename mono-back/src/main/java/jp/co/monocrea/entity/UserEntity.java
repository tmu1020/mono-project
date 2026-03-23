package jp.co.monocrea.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false, unique = true)
    public String userId;

    @Column(name = "user_name", nullable = false)
    public String userName;
}