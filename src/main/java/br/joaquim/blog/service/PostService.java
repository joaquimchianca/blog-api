package br.joaquim.blog.service;

import br.joaquim.blog.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto create(PostDto p);

    List<PostDto> getAll();

    PostDto getById(Long id);

    PostDto update(Long id, PostDto dto);

    void delete(Long id);
}
