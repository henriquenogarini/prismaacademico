package br.edu.utfpr.prismaacademico.lessons.repository;

import br.edu.utfpr.prismaacademico.lessons.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    List<Lesson> findAllByOrderByLessonDateDesc();
    List<Lesson> findByClassGroupIdOrderByLessonDateDesc(UUID classGroupId);
    long countByClassGroupId(UUID classGroupId);
}

