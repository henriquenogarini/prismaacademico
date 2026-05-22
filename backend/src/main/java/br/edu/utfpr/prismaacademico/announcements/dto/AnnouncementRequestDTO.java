package br.edu.utfpr.prismaacademico.announcements.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AnnouncementRequestDTO(
        @NotNull UUID authorId,
        UUID classGroupId,
        @NotBlank String title,
        @NotBlank String content
) {}
