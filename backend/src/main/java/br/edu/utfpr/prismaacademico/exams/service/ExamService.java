package br.edu.utfpr.prismaacademico.exams.service;

import br.edu.utfpr.prismaacademico.classgroups.service.ClassGroupService;
import br.edu.utfpr.prismaacademico.common.exception.BusinessException;
import br.edu.utfpr.prismaacademico.common.exception.ResourceNotFoundException;
import br.edu.utfpr.prismaacademico.exams.dto.*;
import br.edu.utfpr.prismaacademico.exams.entity.Exam;
import br.edu.utfpr.prismaacademico.exams.entity.ExamResult;
import br.edu.utfpr.prismaacademico.exams.repository.ExamRepository;
import br.edu.utfpr.prismaacademico.exams.repository.ExamResultRepository;
import br.edu.utfpr.prismaacademico.students.service.StudentService;
import br.edu.utfpr.prismaacademico.subjects.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;
    private final ClassGroupService classGroupService;
    private final StudentService studentService;
    private final SubjectService subjectService;

    @Transactional(readOnly = true)
    public List<ExamResponseDTO> findAll() {
        return examRepository.findAllByOrderByExamDateDesc().stream().map(ExamResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public ExamResponseDTO findById(UUID id) {
        return ExamResponseDTO.from(findEntityById(id));
    }

    public Exam findEntityById(UUID id) {
        return examRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Simulado", id));
    }

    @Transactional(readOnly = true)
    public List<ExamResponseDTO> findByClass(UUID classId) {
        return examRepository.findByClassGroupIdOrderByExamDateDesc(classId).stream().map(ExamResponseDTO::from).toList();
    }

    @Transactional
    public ExamResponseDTO create(ExamRequestDTO req) {
        Exam e = Exam.builder()
                .classGroup(classGroupService.findEntityById(req.classGroupId()))
                .title(req.title()).examDate(req.examDate()).totalScore(req.totalScore()).build();
        return ExamResponseDTO.from(examRepository.save(e));
    }

    @Transactional
    public ExamResponseDTO update(UUID id, ExamRequestDTO req) {
        Exam e = findEntityById(id);
        e.setClassGroup(classGroupService.findEntityById(req.classGroupId()));
        e.setTitle(req.title()); e.setExamDate(req.examDate()); e.setTotalScore(req.totalScore());
        return ExamResponseDTO.from(examRepository.save(e));
    }

    @Transactional
    public void delete(UUID id) { examRepository.delete(findEntityById(id)); }

    @Transactional(readOnly = true)
    public List<ExamResultResponseDTO> findResults(UUID examId) {
        return examResultRepository.findByExamIdOrderByStudentCandidateFullNameAsc(examId)
                .stream().map(ExamResultResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ExamResultResponseDTO> findResultsByStudent(UUID studentId) {
        return examResultRepository.findByStudentIdOrderByExamExamDateDesc(studentId)
                .stream().map(ExamResultResponseDTO::from).toList();
    }

    @Transactional
    public ExamResultResponseDTO addResult(UUID examId, ExamResultRequestDTO req) {
        Exam exam = findEntityById(examId);
        if (examResultRepository.existsByExamIdAndStudentIdAndSubjectId(examId, req.studentId(), req.subjectId())) {
            throw new BusinessException("Resultado já registrado para este aluno e disciplina neste simulado.");
        }
        ExamResult r = ExamResult.builder()
                .exam(exam)
                .student(studentService.findEntityById(req.studentId()))
                .subject(subjectService.findEntityById(req.subjectId()))
                .score(req.score()).build();
        return ExamResultResponseDTO.from(examResultRepository.save(r));
    }

    @Transactional
    public List<ExamResultResponseDTO> addResultBatch(UUID examId, List<ExamResultRequestDTO> results) {
        return results.stream().map(req -> addResult(examId, req)).toList();
    }
}

