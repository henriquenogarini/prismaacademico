package br.edu.utfpr.prismaacademico.subjects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SubjectRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 100)
        String name,

        @Size(max = 80)
        String area,

        Boolean active
) {}
