package br.edu.utfpr.prismaacademico.candidates.dto;

import br.edu.utfpr.prismaacademico.candidates.enums.CandidateStatus;

public record UpdateCandidateStatusDTO(CandidateStatus status, String observation) {}
