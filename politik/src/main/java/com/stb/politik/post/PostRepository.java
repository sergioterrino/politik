package com.stb.politik.post;

import org.springframework.stereotype.Repository;

import com.stb.politik.user.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    List<Post> findByUser_UserId(Long userId);    
}
