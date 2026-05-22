package br.edu.utfpr.prismaacademico.exams.repository;

import br.edu.utfpr.prismaacademico.exams.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, UUID> {
    List<ExamResult> findByExamIdOrderByStudentCandidateFullNameAsc(UUID examId);
    List<ExamResult> findByStudentIdOrderByExamExamDateDesc(UUID studentId);
    boolean existsByExamIdAndStudentIdAndSubjectId(UUID examId, UUID studentId, UUID subjectId);
}

