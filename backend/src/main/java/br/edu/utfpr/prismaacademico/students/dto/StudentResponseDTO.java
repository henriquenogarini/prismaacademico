package br.edu.utfpr.prismaacademico.students.dto;

import br.edu.utfpr.prismaacademico.students.entity.Student;
import br.edu.utfpr.prismaacademico.students.enums.StudentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record StudentResponseDTO(
        UUID id,
        UUID candidateId,
        String fullName,
        String email,
        String registrationNumber,
        StudentStatus status,
        LocalDate enrollmentDate,
        String className,
        Double attendancePercentage,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static StudentResponseDTO from(Student student) {
        return new StudentResponseDTO(
                student.getId(),
                student.getCandidate() != null ? student.getCandidate().getId() : null,
                student.getCandidate() != null ? student.getCandidate().getFullName() : null,
                student.getCandidate() != null ? student.getCandidate().getEmail() : null,
                student.getRegistrationNumber(),
                student.getStatus(),
                student.getEnrollmentDate(),
                null,
                null,
                student.getCreatedAt(),
                student.getUpdatedAt()
        );
    }

    public static StudentResponseDTO from(Student student, String className, Double attendancePercentage) {
        return new StudentResponseDTO(
                student.getId(),
                student.getCandidate() != null ? student.getCandidate().getId() : null,
                student.getCandidate() != null ? student.getCandidate().getFullName() : null,
                student.getCandidate() != null ? student.getCandidate().getEmail() : null,
                student.getRegistrationNumber(),
                student.getStatus(),
                student.getEnrollmentDate(),
                className,
                attendancePercentage,
                student.getCreatedAt(),
                student.getUpdatedAt()
        );
    }
}
