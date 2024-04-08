package br.joaquim.blog.controller;

import br.joaquim.blog.dto.CommentDto;
import br.joaquim.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable(value = "postId") Long postId,
            @Valid @RequestBody CommentDto dto)
    {
        return new ResponseEntity<>(commentService.createComment(postId, dto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(
           @PathVariable("postId") Long postId
    ) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable("postId") Long postId,
            @PathVariable("id") Long commentId
    ) {
        CommentDto dto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") Long postId,
            @PathVariable("id") Long commentId,
            @Valid @RequestBody CommentDto dto
    ) {
        CommentDto response = commentService.updateComment(postId, commentId, dto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long postId,
            @PathVariable("id") Long commentId
    ) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted sucessfully", HttpStatus.NO_CONTENT);
    }
}
