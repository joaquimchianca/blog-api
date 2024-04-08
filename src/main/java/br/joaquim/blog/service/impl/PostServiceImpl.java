package br.joaquim.blog.service.impl;

import br.joaquim.blog.dto.PostDto;
import br.joaquim.blog.dto.PostResponse;
import br.joaquim.blog.exception.ResourceNotFoundException;
import br.joaquim.blog.model.Post;
import br.joaquim.blog.repository.PostRepository;
import br.joaquim.blog.service.PostService;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper mapper;

    public PostServiceImpl(PostRepository r, ModelMapper m) {
        this.repo = r;
        this.mapper = m;
    }

    @Override
    public PostDto create(PostDto p) {
//        Post newPost = p.mapToPost();
        Post newPost = mapToEntity(p);
        Post savedPost = repo.save(newPost);
        return mapToDto(savedPost);
    }

    @Override
    public PostResponse getAll(
            int pageNo, int pageSize, String sortBy, String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = repo.findAll(pageable);

        List<Post> postList = posts.getContent();
        List<PostDto> content = postList.stream()
                .map(this::mapToDto).toList();

        return new PostResponse(
                content,
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isLast()
        );
    }


    @Override
    public PostDto getById(Long id) {
      Post post = repo.findById(id).orElseThrow(() ->
              new ResourceNotFoundException("Post", "id", id.toString()));
      return mapToDto(post);
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

        return mapToDto(p);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    private Post mapToEntity(PostDto postDto) {
        return mapper.map(postDto, Post.class);
    }

    private PostDto mapToDto(Post post) {
        return mapper.map(post, PostDto.class);
    }


}
