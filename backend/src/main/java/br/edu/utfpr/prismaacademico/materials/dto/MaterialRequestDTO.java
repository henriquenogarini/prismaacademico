package br.edu.utfpr.prismaacademico.materials.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MaterialRequestDTO(
        @NotNull UUID subjectId,
        UUID teacherId,
        @NotBlank String title,
        String description,
        String fileUrl
) {}
