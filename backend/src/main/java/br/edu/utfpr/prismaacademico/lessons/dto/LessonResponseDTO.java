package br.edu.utfpr.prismaacademico.lessons.dto;

import br.edu.utfpr.prismaacademico.lessons.entity.Lesson;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record LessonResponseDTO(
        UUID id,
        UUID classGroupId,
        String classGroupName,
        UUID subjectId,
        String subjectName,
        UUID teacherId,
        String teacherName,
        String title,
        LocalDate lessonDate,
        LocalTime startTime,
        LocalTime endTime,
        LocalDateTime createdAt
) {
    public static LessonResponseDTO from(Lesson l) {
        return new LessonResponseDTO(
                l.getId(),
                l.getClassGroup() != null ? l.getClassGroup().getId() : null,
                l.getClassGroup() != null ? l.getClassGroup().getName() : null,
                l.getSubject() != null ? l.getSubject().getId() : null,
                l.getSubject() != null ? l.getSubject().getName() : null,
                l.getTeacher() != null ? l.getTeacher().getId() : null,
                l.getTeacher() != null && l.getTeacher().getUser() != null ? l.getTeacher().getUser().getName() : null,
                l.getTitle(),
                l.getLessonDate(),
                l.getStartTime(),
                l.getEndTime(),
                l.getCreatedAt()
        );
    }
}
