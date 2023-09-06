package br.joaquim.blog.controller;

import br.joaquim.blog.dto.PostDto;
import br.joaquim.blog.service.PostService;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<PostDto>> getAll() {
        return ResponseEntity.ok(svc.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable Long id) { return ResponseEntity.ok(svc.getById(id));}

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> update(@PathVariable Long id, @RequestBody PostDto dto) {
        return ResponseEntity.ok(svc.update(id, dto));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }
}
