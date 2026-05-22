package br.edu.utfpr.prismaacademico.attendance.dto;

import br.edu.utfpr.prismaacademico.attendance.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AttendanceRequestDTO(
        @NotNull UUID lessonId,
        @NotNull UUID studentId,
        @NotNull AttendanceStatus status,
        String observation
) {}
