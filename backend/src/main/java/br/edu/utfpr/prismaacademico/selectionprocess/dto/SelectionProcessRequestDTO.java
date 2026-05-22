package br.edu.utfpr.prismaacademico.selectionprocess.dto;

import br.edu.utfpr.prismaacademico.selectionprocess.enums.SelectionProcessStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record SelectionProcessRequestDTO(
        @NotBlank(message = "Título é obrigatório")
        @Size(min = 3, max = 200)
        String title,

        @NotNull(message = "Ano é obrigatório")
        @Positive
        Integer year,

        @NotNull(message = "Semestre é obrigatório")
        @Min(1) @Max(2)
        Integer semester,

        LocalDate startDate,
        LocalDate endDate,

        @Positive
        Integer vacancies,

        String description,
        SelectionProcessStatus status
) {}
