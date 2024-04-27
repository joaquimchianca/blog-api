package br.joaquim.blog.dto;

import br.joaquim.blog.model.Post;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class PostDto {
    private Long id;

    @NotEmpty(message = "title must not be empty")
    @Size(min = 2, max = 70, message = "title size must have between 2 and 70 characters")
    private String title;

    @NotEmpty(message = "description must not be empty")
    private String description;

    @NotEmpty(message = "content must not be empty")
    @Size(min = 15, message = "content must have at least 15 characters")
    private String content;
    private Set<CommentDto> comments;

}
