package br.edu.utfpr.prismaacademico.exams.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ExamResultRequestDTO(
        @NotNull UUID studentId,
        @NotNull UUID subjectId,
        BigDecimal score
) {}
