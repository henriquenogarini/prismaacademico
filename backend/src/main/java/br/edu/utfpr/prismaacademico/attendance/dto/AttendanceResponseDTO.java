package br.edu.utfpr.prismaacademico.attendance.dto;

import br.edu.utfpr.prismaacademico.attendance.entity.Attendance;
import br.edu.utfpr.prismaacademico.attendance.enums.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AttendanceResponseDTO(
        UUID id,
        UUID lessonId,
        String lessonTitle,
        LocalDate lessonDate,
        UUID studentId,
        String studentName,
        String registrationNumber,
        AttendanceStatus status,
        String observation,
        LocalDateTime createdAt
) {
    public static AttendanceResponseDTO from(Attendance a) {
        return new AttendanceResponseDTO(
                a.getId(),
                a.getLesson() != null ? a.getLesson().getId() : null,
                a.getLesson() != null ? a.getLesson().getTitle() : null,
                a.getLesson() != null ? a.getLesson().getLessonDate() : null,
                a.getStudent() != null ? a.getStudent().getId() : null,
                a.getStudent() != null && a.getStudent().getCandidate() != null
                        ? a.getStudent().getCandidate().getFullName() : null,
                a.getStudent() != null ? a.getStudent().getRegistrationNumber() : null,
                a.getStatus(),
                a.getObservation(),
                a.getCreatedAt()
        );
    }
}
