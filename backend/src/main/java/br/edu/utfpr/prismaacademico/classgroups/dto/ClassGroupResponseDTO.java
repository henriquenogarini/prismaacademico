package br.edu.utfpr.prismaacademico.classgroups.dto;

import br.edu.utfpr.prismaacademico.classgroups.entity.ClassGroup;
import br.edu.utfpr.prismaacademico.classgroups.enums.ClassStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClassGroupResponseDTO(
        UUID id,
        String name,
        Integer year,
        Integer semester,
        String shift,
        ClassStatus status,
        Long studentsCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ClassGroupResponseDTO from(ClassGroup cg, Long studentsCount) {
        return new ClassGroupResponseDTO(
                cg.getId(), cg.getName(), cg.getYear(), cg.getSemester(),
                cg.getShift(), cg.getStatus(), studentsCount,
                cg.getCreatedAt(), cg.getUpdatedAt()
        );
    }

    public static ClassGroupResponseDTO from(ClassGroup cg) {
        return from(cg, null);
    }
}
