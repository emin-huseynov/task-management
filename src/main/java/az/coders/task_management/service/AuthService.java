
package az.coders.task_management.service;

import az.coders.task_management.dto.request.LoginRequestDto;
import az.coders.task_management.dto.response.LoginResponseDto;
import az.coders.task_management.dto.request.RegisterRequestDto;
import az.coders.task_management.dto.response.RegisterResponseDto;
import az.coders.task_management.entity.UserEntity;
import az.coders.task_management.exception.DuplicateResourceException;
import az.coders.task_management.exception.ResourceNotFoundException;
import az.coders.task_management.mapper.UserMapper;
import az.coders.task_management.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public RegisterResponseDto register(RegisterRequestDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        UserEntity user = userMapper.toEntity(dto);

        user.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );

        UserEntity savedUser = userRepository.save(user);

        return userMapper.toResponseDto(savedUser);
    }

    public LoginResponseDto login(LoginRequestDto dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        UserEntity user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        String token = jwtService.generateToken(user);

        LoginResponseDto response = new LoginResponseDto();
        response.setAccessToken(token);
        response.setTokenType("Bearer");

        return response;
    }
}
