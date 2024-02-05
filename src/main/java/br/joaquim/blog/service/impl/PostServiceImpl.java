package br.joaquim.blog.service.impl;

import br.joaquim.blog.dto.PostDto;
import br.joaquim.blog.exception.ResourceNotFoundException;
import br.joaquim.blog.model.Post;
import br.joaquim.blog.repository.PostRepository;
import br.joaquim.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Page<PostDto> getAll(
            int pageNo, int pageSize, String sortBy, String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = repo.findAll(pageable);

        return posts.map(PostDto::new);
    }

    @Override
    public PostDto getById(Long id) {
      return new PostDto(repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Post", "id", id.toString()
                )));
    }

    @Override
    public PostDto update(Long id, PostDto dto) {
        Post p = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Post", "id", id.toString()
                ));
        if (dto.getTitle() != null) {
            p.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            p.setDescription(dto.getDescription());
        }
        if (dto.getContent() != null) {
            p.setContent(dto.getContent());
        }
        if (dto.getTitle() != null ||
                dto.getDescription() != null ||
                dto.getContent() != null) {
            repo.save(p);
        }

        return new PostDto(p);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }


}
