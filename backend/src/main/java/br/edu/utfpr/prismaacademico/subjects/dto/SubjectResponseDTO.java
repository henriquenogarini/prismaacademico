package br.edu.utfpr.prismaacademico.subjects.dto;

import br.edu.utfpr.prismaacademico.subjects.entity.Subject;

import java.time.LocalDateTime;
import java.util.UUID;

public record SubjectResponseDTO(
        UUID id,
        String name,
        String area,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static SubjectResponseDTO from(Subject s) {
        return new SubjectResponseDTO(s.getId(), s.getName(), s.getArea(), s.isActive(), s.getCreatedAt(), s.getUpdatedAt());
    }
}
