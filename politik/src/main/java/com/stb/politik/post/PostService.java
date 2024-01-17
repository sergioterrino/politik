package com.stb.politik.post;

import java.util.List;

import com.stb.politik.user.User;

public interface PostService {

    List<Post> getAllPosts();
    void savePost(Post post);
    List<Post> getPostsByUserId(Long userId);
}
