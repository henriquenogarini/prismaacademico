package br.edu.utfpr.prismaacademico.candidates.dto;

import br.edu.utfpr.prismaacademico.candidates.enums.CandidateDocumentType;
import jakarta.validation.constraints.NotNull;

public record CandidateDocumentRequestDTO(
        @NotNull(message = "Tipo de documento é obrigatório")
        CandidateDocumentType documentType,
        String fileUrl,
        String observation
) {}
