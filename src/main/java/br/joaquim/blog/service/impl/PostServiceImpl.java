package br.joaquim.blog.service.impl;

import br.joaquim.blog.dto.PostDto;
import br.joaquim.blog.model.Post;
import br.joaquim.blog.repository.PostRepository;
import br.joaquim.blog.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository repo;

    public PostServiceImpl(PostRepository r) {
        this.repo = r;
    }

    @Override
    public PostDto create(PostDto p) {
        Post newPost = p.mapToPost();
        Post savedPost = repo.save(newPost);
        return new PostDto(savedPost);
    }
}
