package br.edu.utfpr.prismaacademico.candidates.dto;

import br.edu.utfpr.prismaacademico.candidates.entity.CandidateDocument;
import br.edu.utfpr.prismaacademico.candidates.enums.CandidateDocumentType;
import br.edu.utfpr.prismaacademico.candidates.enums.DocumentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record CandidateDocumentResponseDTO(
        UUID id,
        UUID candidateId,
        CandidateDocumentType documentType,
        String fileUrl,
        DocumentStatus status,
        String observation,
        LocalDateTime uploadedAt
) {
    public static CandidateDocumentResponseDTO from(CandidateDocument doc) {
        return new CandidateDocumentResponseDTO(
                doc.getId(),
                doc.getCandidate() != null ? doc.getCandidate().getId() : null,
                doc.getDocumentType(),
                doc.getFileUrl(),
                doc.getStatus(),
                doc.getObservation(),
                doc.getUploadedAt()
        );
    }
}
