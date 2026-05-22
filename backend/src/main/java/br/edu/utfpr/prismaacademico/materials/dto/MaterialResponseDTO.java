package br.edu.utfpr.prismaacademico.materials.dto;

import br.edu.utfpr.prismaacademico.materials.entity.Material;

import java.time.LocalDateTime;
import java.util.UUID;

public record MaterialResponseDTO(
        UUID id,
        UUID subjectId,
        String subjectName,
        UUID teacherId,
        String teacherName,
        String title,
        String description,
        String fileUrl,
        LocalDateTime createdAt
) {
    public static MaterialResponseDTO from(Material m) {
        return new MaterialResponseDTO(
                m.getId(),
                m.getSubject() != null ? m.getSubject().getId() : null,
                m.getSubject() != null ? m.getSubject().getName() : null,
                m.getTeacher() != null ? m.getTeacher().getId() : null,
                m.getTeacher() != null && m.getTeacher().getUser() != null ? m.getTeacher().getUser().getName() : null,
                m.getTitle(),
                m.getDescription(),
                m.getFileUrl(),
                m.getCreatedAt()
        );
    }
}
