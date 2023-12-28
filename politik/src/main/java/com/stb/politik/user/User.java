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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true, nullable = false, length = 25)
    private String username;

    @Column(name = "role", nullable = false, length = 7)
    private String role;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "lastname", nullable = false, length = 30)
    private String lastname;

    @Column(name = "phone", unique = true, nullable = true, length = 9)
    private String phone;

    @Column(name = "email", unique = true, nullable = true, length = 50)
    private String email;

    @Column(name = "created_at", nullable = true, length = 15)
    private String created_at;

    @Column(name = "birthday", nullable = false, length = 15)
    private String birthday;

    @Column(name = "dni", nullable = true, length = 9)
    private String dni;

}