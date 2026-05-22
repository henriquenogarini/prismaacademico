package br.edu.utfpr.prismaacademico.selectionprocess.dto;

import br.edu.utfpr.prismaacademico.selectionprocess.entity.SelectionProcess;
import br.edu.utfpr.prismaacademico.selectionprocess.enums.SelectionProcessStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record SelectionProcessResponseDTO(
        UUID id,
        String title,
        Integer year,
        Integer semester,
        LocalDate startDate,
        LocalDate endDate,
        Integer vacancies,
        String description,
        SelectionProcessStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static SelectionProcessResponseDTO from(SelectionProcess sp) {
        return new SelectionProcessResponseDTO(
                sp.getId(), sp.getTitle(), sp.getYear(), sp.getSemester(),
                sp.getStartDate(), sp.getEndDate(), sp.getVacancies(),
                sp.getDescription(), sp.getStatus(), sp.getCreatedAt(), sp.getUpdatedAt()
        );
    }
}
