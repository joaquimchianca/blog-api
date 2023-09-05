package br.joaquim.blog.dto;

import br.joaquim.blog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;

    public Post mapToPost() {
        Post newPost = new Post();
        newPost.setTitle(this.title);
        newPost.setContent(this.content);
        newPost.setDescription(this.description);
        return newPost;
    }

    public PostDto(Post p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.content = p.getContent();
        this.description = p.getDescription();
    }
}
