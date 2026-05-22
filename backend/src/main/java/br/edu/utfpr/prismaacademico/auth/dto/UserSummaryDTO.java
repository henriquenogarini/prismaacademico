package br.edu.utfpr.prismaacademico.auth.dto;

import br.edu.utfpr.prismaacademico.users.enums.UserRole;

import java.util.UUID;

public record UserSummaryDTO(UUID id, String name, String email, UserRole role) {}

