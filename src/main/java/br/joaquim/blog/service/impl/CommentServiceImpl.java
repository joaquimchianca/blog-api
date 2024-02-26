package br.joaquim.blog.service.impl;

import br.joaquim.blog.dto.CommentDto;
import br.joaquim.blog.exception.BlogApiException;
import br.joaquim.blog.exception.ResourceNotFoundException;
import br.joaquim.blog.model.Comment;
import br.joaquim.blog.model.Post;
import br.joaquim.blog.repository.CommentRepository;
import br.joaquim.blog.repository.PostRepository;
import br.joaquim.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepo;
    private PostRepository postRepo;

    public CommentServiceImpl(CommentRepository c, PostRepository p) {
        this.commentRepo = c;
        this.postRepo = p;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto dto) {
        Comment comment = mapToEntity(dto);
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId.toString())
        );
        comment.setPost(post);
        return mapToDto(commentRepo.save(comment));
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);


        return comments.stream().map(comment -> mapToDto(comment))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId.toString())
        );

        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Comment", "id", commentId.toString()
                )
        );

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post.");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto dto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId.toString())
        );

        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Comment", "id", commentId.toString()
                )
        );

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post.");
        }

        if (dto.getBody() != null) {
            comment.setBody(dto.getBody());
        }

        if (dto.getEmail() != null) {
            comment.setEmail(dto.getEmail());
        }

        if (dto.getName() != null) {
            comment.setName(dto.getName());
        }

        return mapToDto(commentRepo.save(comment));
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }

    private Comment mapToEntity(CommentDto dto) {
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setBody(dto.getBody());
        comment.setEmail(dto.getEmail());
        comment.setName(dto.getName());
        return comment;
    }
}
