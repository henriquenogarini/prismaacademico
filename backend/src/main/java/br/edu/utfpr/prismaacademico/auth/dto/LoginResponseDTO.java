package br.edu.utfpr.prismaacademico.auth.dto;

public record LoginResponseDTO(
        String accessToken,
        String tokenType,
        long expiresIn,
        UserSummaryDTO user
) {}
