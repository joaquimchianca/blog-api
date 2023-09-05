package br.joaquim.blog.controller;

import br.joaquim.blog.dto.PostDto;
import br.joaquim.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService svc;

    public PostController(PostService s) {
        this.svc = s;
    }

    @PostMapping
    public ResponseEntity<PostDto> create(@RequestBody PostDto p) {
        return new ResponseEntity<>(svc.create(p), HttpStatus.CREATED);
    }
}
