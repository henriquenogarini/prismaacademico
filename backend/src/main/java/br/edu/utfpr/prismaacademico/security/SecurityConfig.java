package br.edu.utfpr.prismaacademico.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CorsConfigurationSource corsConfigurationSource;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/login",
            "/api/public/**",
            "/api/candidates/public-application",
            "/api/candidates/status/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/actuator/health",
            "/actuator/info"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Admin e Coordination: acesso total
                        .requestMatchers("/api/users/**").hasAnyRole("ADMIN", "COORDINATION")
                        .requestMatchers("/api/selection-processes/**").hasAnyRole("ADMIN", "COORDINATION")
                        .requestMatchers(HttpMethod.POST, "/api/candidates/**").hasAnyRole("ADMIN", "COORDINATION")
                        .requestMatchers(HttpMethod.PUT, "/api/candidates/**").hasAnyRole("ADMIN", "COORDINATION")
                        .requestMatchers(HttpMethod.PATCH, "/api/candidates/**").hasAnyRole("ADMIN", "COORDINATION")
                        .requestMatchers(HttpMethod.GET, "/api/candidates/**").hasAnyRole("ADMIN", "COORDINATION")

                        // Relatórios: Admin e Coordination
                        .requestMatchers("/api/reports/**").hasAnyRole("ADMIN", "COORDINATION")

                        // Turmas: Admin, Coordination e Teacher (leitura)
                        .requestMatchers(HttpMethod.GET, "/api/classes/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/classes/**").hasAnyRole("ADMIN", "COORDINATION")
                        .requestMatchers(HttpMethod.PUT, "/api/classes/**").hasAnyRole("ADMIN", "COORDINATION")
                        .requestMatchers(HttpMethod.DELETE, "/api/classes/**").hasAnyRole("ADMIN", "COORDINATION")

                        // Disciplinas
                        .requestMatchers(HttpMethod.GET, "/api/subjects/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/subjects/**").hasAnyRole("ADMIN", "COORDINATION")
                        .requestMatchers(HttpMethod.PUT, "/api/subjects/**").hasAnyRole("ADMIN", "COORDINATION")
                        .requestMatchers(HttpMethod.DELETE, "/api/subjects/**").hasAnyRole("ADMIN", "COORDINATION")

                        // Professores
                        .requestMatchers(HttpMethod.GET, "/api/teachers/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api/teachers/**").hasAnyRole("ADMIN", "COORDINATION")
                        .requestMatchers(HttpMethod.PUT, "/api/teachers/**").hasAnyRole("ADMIN", "COORDINATION")
                        .requestMatchers(HttpMethod.PATCH, "/api/teachers/**").hasAnyRole("ADMIN", "COORDINATION")

                        // Aulas
                        .requestMatchers(HttpMethod.GET, "/api/lessons/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/lessons/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/lessons/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/lessons/**").hasAnyRole("ADMIN", "COORDINATION")

                        // Frequência
                        .requestMatchers(HttpMethod.GET, "/api/attendance/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/attendance/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER")

                        // Materiais
                        .requestMatchers(HttpMethod.GET, "/api/materials/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/materials/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/materials/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/materials/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER")

                        // Comunicados
                        .requestMatchers(HttpMethod.GET, "/api/announcements/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/announcements/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/announcements/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/announcements/**").hasAnyRole("ADMIN", "COORDINATION")

                        // Simulados
                        .requestMatchers(HttpMethod.GET, "/api/exams/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/exams/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/exams/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/exams/**").hasAnyRole("ADMIN", "COORDINATION")

                        // Alunos
                        .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole("ADMIN", "COORDINATION", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/students/**").hasAnyRole("ADMIN", "COORDINATION")
                        .requestMatchers(HttpMethod.PUT, "/api/students/**").hasAnyRole("ADMIN", "COORDINATION")
                        .requestMatchers(HttpMethod.PATCH, "/api/students/**").hasAnyRole("ADMIN", "COORDINATION")

                        // Auth endpoints (authenticated)
                        .requestMatchers("/api/auth/**").authenticated()

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}

