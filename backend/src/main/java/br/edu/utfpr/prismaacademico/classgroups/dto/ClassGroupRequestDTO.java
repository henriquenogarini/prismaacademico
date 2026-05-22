package br.edu.utfpr.prismaacademico.classgroups.dto;

import br.edu.utfpr.prismaacademico.classgroups.enums.ClassStatus;
import jakarta.validation.constraints.*;

public record ClassGroupRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 100)
        String name,

        @NotNull(message = "Ano é obrigatório")
        @Positive
        Integer year,

        @NotNull(message = "Semestre é obrigatório")
        @Min(1) @Max(2)
        Integer semester,

        String shift,
        ClassStatus status
) {}
