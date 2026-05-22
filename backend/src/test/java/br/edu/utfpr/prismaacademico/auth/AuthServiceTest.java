package br.edu.utfpr.prismaacademico.auth;

import br.edu.utfpr.prismaacademico.auth.dto.LoginRequestDTO;
import br.edu.utfpr.prismaacademico.auth.dto.LoginResponseDTO;
import br.edu.utfpr.prismaacademico.auth.dto.MeResponseDTO;
import br.edu.utfpr.prismaacademico.auth.service.AuthService;
import br.edu.utfpr.prismaacademico.common.exception.UnauthorizedException;
import br.edu.utfpr.prismaacademico.common.redis.RedisTokenService;
import br.edu.utfpr.prismaacademico.security.JwtService;
import br.edu.utfpr.prismaacademico.users.entity.User;
import br.edu.utfpr.prismaacademico.users.enums.UserRole;
import br.edu.utfpr.prismaacademico.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService - Testes Unitários")
class AuthServiceTest {

    @Mock private AuthenticationManager authenticationManager;
    @Mock private UserRepository userRepository;
    @Mock private JwtService jwtService;
    @Mock private UserDetailsService userDetailsService;
    @Mock private RedisTokenService redisTokenService;

    @InjectMocks
    private AuthService authService;

    private User mockUser;
    private UserDetails mockUserDetails;

    @BeforeEach
    void setUp() {
        mockUser = User.builder()
                .id(UUID.randomUUID())
                .name("Test User")
                .email("test@prisma.com")
                .passwordHash("hashedpassword")
                .role(UserRole.COORDINATION)
                .active(true)
                .build();

        mockUserDetails = org.springframework.security.core.userdetails.User
                .withUsername("test@prisma.com")
                .password("hashedpassword")
                .authorities("ROLE_COORDINATION")
                .build();
    }

    @Test
    @DisplayName("Login com credenciais válidas deve retornar token")
    void login_validCredentials_returnsToken() {
        var request = new LoginRequestDTO("test@prisma.com", "123456");

        when(authenticationManager.authenticate(any())).thenReturn(
                new UsernamePasswordAuthenticationToken("test@prisma.com", "123456"));
        when(userRepository.findByEmailAndActiveTrue("test@prisma.com"))
                .thenReturn(Optional.of(mockUser));
        when(userDetailsService.loadUserByUsername("test@prisma.com"))
                .thenReturn(mockUserDetails);
        when(jwtService.generateToken(any(), any())).thenReturn("mocked.jwt.token");
        when(jwtService.getExpirationMinutes()).thenReturn(120L);

        LoginResponseDTO response = authService.login(request);

        assertThat(response).isNotNull();
        assertThat(response.accessToken()).isEqualTo("mocked.jwt.token");
        assertThat(response.tokenType()).isEqualTo("Bearer");
        assertThat(response.user().email()).isEqualTo("test@prisma.com");
        assertThat(response.user().role()).isEqualTo(UserRole.COORDINATION);
        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    @DisplayName("Login com usuário não encontrado deve lançar exceção")
    void login_userNotFound_throwsException() {
        var request = new LoginRequestDTO("notfound@prisma.com", "123456");

        when(authenticationManager.authenticate(any())).thenReturn(
                new UsernamePasswordAuthenticationToken("notfound@prisma.com", "123456"));
        when(userRepository.findByEmailAndActiveTrue("notfound@prisma.com"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(UnauthorizedException.class);
    }

    @Test
    @DisplayName("Login com credenciais inválidas deve lançar BadCredentialsException")
    void login_invalidCredentials_throwsBadCredentials() {
        var request = new LoginRequestDTO("test@prisma.com", "wrongpassword");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    @DisplayName("Logout deve adicionar token à blacklist")
    void logout_validToken_blacklistsToken() {
        String token = "valid.jwt.token";
        when(jwtService.getExpirationInSeconds(token)).thenReturn(3600L);

        authService.logout(token);

        verify(redisTokenService, times(1)).blacklistToken(token, 3600L);
    }

    @Test
    @DisplayName("getMe deve retornar dados do usuário autenticado")
    void getMe_existingUser_returnsUserData() {
        when(userRepository.findByEmailAndActiveTrue("test@prisma.com"))
                .thenReturn(Optional.of(mockUser));

        MeResponseDTO result = authService.getMe("test@prisma.com");

        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo("test@prisma.com");
        assertThat(result.role()).isEqualTo(UserRole.COORDINATION);
    }
}
