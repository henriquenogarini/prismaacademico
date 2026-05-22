package br.edu.utfpr.prismaacademico.attendance.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record AttendanceBatchRequestDTO(
        @NotNull UUID lessonId,
        @NotNull List<AttendanceRecordDTO> records
) {}
