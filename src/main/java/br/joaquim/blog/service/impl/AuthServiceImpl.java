package br.joaquim.blog.service.impl;

import br.joaquim.blog.dto.LoginDto;
import br.joaquim.blog.dto.RegisterDto;
import br.joaquim.blog.exception.BlogApiException;
import br.joaquim.blog.model.Role;
import br.joaquim.blog.model.User;
import br.joaquim.blog.repository.RoleRepository;
import br.joaquim.blog.repository.UserRepository;
import br.joaquim.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.usernameOrEmail(), loginDto.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User logged-in sucessfully.";
    }

    @Override
    public String register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.username())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username already exists in " +
                    "database.");
        }

        if (userRepository.existsByEmail(registerDto.email())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email already exists in database.");
        }

        User user = new User();
        user.setName(registerDto.name());
        user.setEmail(registerDto.email());
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        user.setUsername(registerDto.username());

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER").get();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered sucessfully.";

    }
}
