package com.stb.politik.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true, nullable = false, length = 25)
    private String username;

    @Column(name = "phone", unique = true, length = 9)
    private String phone;

    @Column(name = "email", unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "lastname", nullable = false, length = 30)
    private String lastname;

    @Column(name = "birthday", nullable = false, length = 15)
    private String birthday;

    @Column(name = "created_at", nullable = false, length = 15)
    private String created_at;

    
}
