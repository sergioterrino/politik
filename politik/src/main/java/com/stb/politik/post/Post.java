package com.stb.politik.post;

import java.time.LocalDateTime;

import com.stb.politik.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "text", length = 2000)
    private String text;

    @Column(name = "imagePath")
    private String imagePath;

    @Column(name = "videoPath")
    private String videoPath;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
