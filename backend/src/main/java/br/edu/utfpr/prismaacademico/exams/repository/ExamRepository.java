package br.edu.utfpr.prismaacademico.exams.repository;

import br.edu.utfpr.prismaacademico.exams.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExamRepository extends JpaRepository<Exam, UUID> {
    List<Exam> findAllByOrderByExamDateDesc();
    List<Exam> findByClassGroupIdOrderByExamDateDesc(UUID classGroupId);
}

