package br.edu.utfpr.prismaacademico.exams.dto;

import br.edu.utfpr.prismaacademico.exams.entity.ExamResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ExamResultResponseDTO(
        UUID id,
        UUID examId,
        String examTitle,
        UUID studentId,
        String studentName,
        String registrationNumber,
        UUID subjectId,
        String subjectName,
        BigDecimal score,
        LocalDateTime createdAt
) {
    public static ExamResultResponseDTO from(ExamResult r) {
        return new ExamResultResponseDTO(
                r.getId(),
                r.getExam() != null ? r.getExam().getId() : null,
                r.getExam() != null ? r.getExam().getTitle() : null,
                r.getStudent() != null ? r.getStudent().getId() : null,
                r.getStudent() != null && r.getStudent().getCandidate() != null
                        ? r.getStudent().getCandidate().getFullName() : null,
                r.getStudent() != null ? r.getStudent().getRegistrationNumber() : null,
                r.getSubject() != null ? r.getSubject().getId() : null,
                r.getSubject() != null ? r.getSubject().getName() : null,
                r.getScore(),
                r.getCreatedAt()
        );
    }
}
