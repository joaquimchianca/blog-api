package br.joaquim.blog.dto;

public record RegisterDto(
        String name,
        String email,
        String username,
        String password
) {
}
