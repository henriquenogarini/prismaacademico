package br.edu.utfpr.prismaacademico.auth.dto;

import br.edu.utfpr.prismaacademico.users.enums.UserRole;

import java.util.UUID;

public record MeResponseDTO(UUID id, String name, String email, UserRole role, boolean active) {}

