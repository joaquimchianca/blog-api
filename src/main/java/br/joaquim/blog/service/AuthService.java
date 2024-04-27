package br.joaquim.blog.service;

import br.joaquim.blog.dto.LoginDto;
import br.joaquim.blog.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
