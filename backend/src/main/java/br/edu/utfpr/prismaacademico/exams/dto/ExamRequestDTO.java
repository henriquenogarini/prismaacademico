package br.edu.utfpr.prismaacademico.exams.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExamRequestDTO(
        @NotNull UUID classGroupId,
        @NotBlank String title,
        LocalDate examDate,
        BigDecimal totalScore
) {}
