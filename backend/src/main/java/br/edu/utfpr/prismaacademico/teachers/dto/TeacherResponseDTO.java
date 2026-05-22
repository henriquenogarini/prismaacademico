package br.edu.utfpr.prismaacademico.teachers.dto;

import br.edu.utfpr.prismaacademico.teachers.entity.Teacher;

import java.time.LocalDateTime;
import java.util.UUID;

public record TeacherResponseDTO(
        UUID id,
        UUID userId,
        String name,
        String email,
        String course,
        String institution,
        boolean active,
        LocalDateTime createdAt
) {
    public static TeacherResponseDTO from(Teacher t) {
        return new TeacherResponseDTO(
                t.getId(),
                t.getUser() != null ? t.getUser().getId() : null,
                t.getUser() != null ? t.getUser().getName() : null,
                t.getUser() != null ? t.getUser().getEmail() : null,
                t.getCourse(),
                t.getInstitution(),
                t.isActive(),
                t.getCreatedAt()
        );
    }
}
