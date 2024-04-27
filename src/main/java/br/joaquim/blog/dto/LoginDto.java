package br.joaquim.blog.dto;

public record LoginDto(
        String usernameOrEmail,
        String password
) {}
