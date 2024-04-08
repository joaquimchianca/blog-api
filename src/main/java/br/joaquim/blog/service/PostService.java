package br.joaquim.blog.service;

import br.joaquim.blog.dto.PostDto;
import br.joaquim.blog.dto.PostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    PostDto create(PostDto p);

    PostResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getById(Long id);

    PostDto update(Long id, PostDto dto);

    void delete(Long id);
}
