package br.edu.utfpr.prismaacademico.teachers.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TeacherRequestDTO(
        @NotNull(message = "Usuário é obrigatório")
        UUID userId,
        String course,
        String institution,
        Boolean active
) {}
