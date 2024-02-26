package br.joaquim.blog.service;

import br.joaquim.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId, CommentDto dto);

    List<CommentDto> getCommentsByPostId(Long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId, Long commentId, CommentDto dto);
}
