package com.stb.politik.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stb.politik.credentials.Credentials;
import com.stb.politik.post.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @Column(name = "rol", nullable = false, length = 7)
    private String rol;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "lastname", nullable = false, length = 30)
    private String lastname;

    @Column(name = "phone", unique = true, nullable = true, length = 9)
    private String phone;

    @Column(name = "email", unique = true, nullable = true, length = 50)
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "dni", length = 9)
    private String dni;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Credentials credentials;

    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", rol=" + rol + ", name=" + name + ", lastname="
                + lastname + ", phone=" + phone + ", email=" + email + ", createdAt=" + createdAt + ", birthday="
                + birthday + ", dni=" + dni + "]";
    }
}