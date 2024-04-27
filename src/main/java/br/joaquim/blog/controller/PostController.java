package br.joaquim.blog.controller;

import br.joaquim.blog.dto.PostDto;
import br.joaquim.blog.dto.PostResponse;
import br.joaquim.blog.service.PostService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> create(@Valid @RequestBody PostDto p) {
        return new ResponseEntity<>(svc.create(p), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAll(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        return ResponseEntity.ok(svc.getAll(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable Long id) { return ResponseEntity.ok(svc.getById(id));}

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> update(@PathVariable Long id, @Valid @RequestBody PostDto dto) {
        return ResponseEntity.ok(svc.update(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }
}
