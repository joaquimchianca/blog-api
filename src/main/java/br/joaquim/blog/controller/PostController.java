package br.joaquim.blog.controller;

import br.joaquim.blog.dto.PostDto;
import br.joaquim.blog.service.PostService;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.joaquim.blog.utils.AppConstants.*;

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
    public ResponseEntity<Page<PostDto>> getAll(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        return ResponseEntity.ok(svc.getAll(pageNo, pageSize, sortBy, sortDir));
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
