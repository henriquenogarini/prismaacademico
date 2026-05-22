package br.edu.utfpr.prismaacademico.announcements.dto;

import br.edu.utfpr.prismaacademico.announcements.entity.Announcement;

import java.time.LocalDateTime;
import java.util.UUID;

public record AnnouncementResponseDTO(
        UUID id,
        UUID authorId,
        String authorName,
        UUID classGroupId,
        String classGroupName,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AnnouncementResponseDTO from(Announcement a) {
        return new AnnouncementResponseDTO(
                a.getId(),
                a.getAuthor() != null ? a.getAuthor().getId() : null,
                a.getAuthor() != null ? a.getAuthor().getName() : null,
                a.getClassGroup() != null ? a.getClassGroup().getId() : null,
                a.getClassGroup() != null ? a.getClassGroup().getName() : null,
                a.getTitle(),
                a.getContent(),
                a.getCreatedAt(),
                a.getUpdatedAt()
        );
    }
}
