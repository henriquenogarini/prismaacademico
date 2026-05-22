package br.edu.utfpr.prismaacademico.exams.dto;

import br.edu.utfpr.prismaacademico.exams.entity.Exam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ExamResponseDTO(
        UUID id,
        UUID classGroupId,
        String classGroupName,
        String title,
        LocalDate examDate,
        BigDecimal totalScore,
        LocalDateTime createdAt
) {
    public static ExamResponseDTO from(Exam e) {
        return new ExamResponseDTO(
                e.getId(),
                e.getClassGroup() != null ? e.getClassGroup().getId() : null,
                e.getClassGroup() != null ? e.getClassGroup().getName() : null,
                e.getTitle(),
                e.getExamDate(),
                e.getTotalScore(),
                e.getCreatedAt()
        );
    }
}
