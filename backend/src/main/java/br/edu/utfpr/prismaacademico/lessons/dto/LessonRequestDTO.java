package br.edu.utfpr.prismaacademico.lessons.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record LessonRequestDTO(
        @NotNull UUID classGroupId,
        @NotNull UUID subjectId,
        UUID teacherId,
        @NotBlank String title,
        @NotNull LocalDate lessonDate,
        LocalTime startTime,
        LocalTime endTime
) {}
