package com.stb.politik.post;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();
    void savePost(Post post);
    
}
