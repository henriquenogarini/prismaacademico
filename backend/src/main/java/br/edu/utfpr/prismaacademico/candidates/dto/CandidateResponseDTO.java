package br.edu.utfpr.prismaacademico.candidates.dto;

import br.edu.utfpr.prismaacademico.candidates.entity.Candidate;
import br.edu.utfpr.prismaacademico.candidates.enums.CandidateStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CandidateResponseDTO(
        UUID id,
        UUID selectionProcessId,
        String selectionProcessTitle,
        String fullName,
        String documentNumber,
        LocalDate birthDate,
        String phone,
        String email,
        String schoolName,
        String schoolYear,
        Boolean publicSchool,
        BigDecimal incomePerCapita,
        String observation,
        CandidateStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CandidateResponseDTO from(Candidate c) {
        return new CandidateResponseDTO(
                c.getId(),
                c.getSelectionProcess() != null ? c.getSelectionProcess().getId() : null,
                c.getSelectionProcess() != null ? c.getSelectionProcess().getTitle() : null,
                c.getFullName(), c.getDocumentNumber(), c.getBirthDate(),
                c.getPhone(), c.getEmail(), c.getSchoolName(), c.getSchoolYear(),
                c.getPublicSchool(), c.getIncomePerCapita(), c.getObservation(),
                c.getStatus(), c.getCreatedAt(), c.getUpdatedAt()
        );
    }
}
