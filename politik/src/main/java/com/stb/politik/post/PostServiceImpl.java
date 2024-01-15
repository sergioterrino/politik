package com.stb.politik.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return this.postRepository.findAll();
    }

    @Override
    public void savePost(Post post) {
        this.postRepository.save(post);
    }

    
}
