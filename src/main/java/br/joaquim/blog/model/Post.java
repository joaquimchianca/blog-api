package br.joaquim.blog.model;

import br.joaquim.blog.dto.PostDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "tb_post",uniqueConstraints = {@UniqueConstraint(columnNames = "title")})
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "title")
    private String title;
    @Column(nullable = false, name = "description")
    private String description;
    @Column(name = "content",nullable = false, columnDefinition = "TEXT")
    private String content;

    public PostDto mapToDto() {
        PostDto dto = new PostDto();
        dto.setId(this.id);
        dto.setTitle(this.title);
        dto.setContent(this.content);
        dto.setDescription(this.description);
        return dto;
    }

    public Post(PostDto p) {
        if ( id != null) this.id = p.getId();
        this.title = p.getTitle();
        this.content = p.getContent();
        this.description = p.getDescription();
    }
}
