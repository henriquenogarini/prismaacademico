package br.edu.utfpr.prismaacademico.auth.service;

import br.edu.utfpr.prismaacademico.auth.dto.LoginRequestDTO;
import br.edu.utfpr.prismaacademico.auth.dto.LoginResponseDTO;
import br.edu.utfpr.prismaacademico.auth.dto.MeResponseDTO;
import br.edu.utfpr.prismaacademico.auth.dto.UserSummaryDTO;
import br.edu.utfpr.prismaacademico.common.exception.UnauthorizedException;
import br.edu.utfpr.prismaacademico.common.redis.RedisTokenService;
import br.edu.utfpr.prismaacademico.security.JwtService;
import br.edu.utfpr.prismaacademico.users.entity.User;
import br.edu.utfpr.prismaacademico.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final RedisTokenService redisTokenService;

    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email().toLowerCase().trim(),
                        request.password()
                )
        );

        User user = userRepository.findByEmailAndActiveTrue(request.email().toLowerCase().trim())
                .orElseThrow(() -> new UnauthorizedException("Usuário não encontrado ou inativo."));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("userId", user.getId().toString());

        String token = jwtService.generateToken(extraClaims, userDetails);
        long expiresIn = jwtService.getExpirationMinutes() * 60;

        log.info("Login realizado com sucesso para: {}", user.getEmail());

        return new LoginResponseDTO(
                token,
                "Bearer",
                expiresIn,
                new UserSummaryDTO(user.getId(), user.getName(), user.getEmail(), user.getRole())
        );
    }

    @Transactional(readOnly = true)
    public MeResponseDTO getMe(String userEmail) {
        User user = userRepository.findByEmailAndActiveTrue(userEmail)
                .orElseThrow(() -> new UnauthorizedException("Usuário não encontrado."));

        return new MeResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.isActive());
    }

    public void logout(String token) {
        if (token != null && !token.isBlank()) {
            long expirationSeconds = jwtService.getExpirationInSeconds(token);
            redisTokenService.blacklistToken(token, expirationSeconds);
            log.info("Logout realizado. Token adicionado à blacklist.");
        }
    }
}

