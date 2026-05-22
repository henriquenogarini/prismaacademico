package br.edu.utfpr.prismaacademico.attendance.service;

import br.edu.utfpr.prismaacademico.attendance.dto.AttendanceBatchRequestDTO;
import br.edu.utfpr.prismaacademico.attendance.dto.AttendanceRecordDTO;
import br.edu.utfpr.prismaacademico.attendance.dto.AttendanceRequestDTO;
import br.edu.utfpr.prismaacademico.attendance.dto.AttendanceResponseDTO;
import br.edu.utfpr.prismaacademico.attendance.entity.Attendance;
import br.edu.utfpr.prismaacademico.attendance.repository.AttendanceRepository;
import br.edu.utfpr.prismaacademico.common.exception.BusinessException;
import br.edu.utfpr.prismaacademico.lessons.entity.Lesson;
import br.edu.utfpr.prismaacademico.lessons.service.LessonService;
import br.edu.utfpr.prismaacademico.students.entity.Student;
import br.edu.utfpr.prismaacademico.students.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository repository;
    private final LessonService lessonService;
    private final StudentService studentService;

    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> findAll() {
        return repository.findAllByOrderByCreatedAtDesc().stream().map(AttendanceResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> findByLesson(UUID lessonId) {
        return repository.findByLessonIdOrderByStudentCandidateFullNameAsc(lessonId)
                .stream().map(AttendanceResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> findByStudent(UUID studentId) {
        return repository.findByStudentIdOrderByLessonLessonDateDesc(studentId)
                .stream().map(AttendanceResponseDTO::from).toList();
    }

    @Transactional
    public AttendanceResponseDTO register(AttendanceRequestDTO req) {
        if (repository.existsByLessonIdAndStudentId(req.lessonId(), req.studentId())) {
            throw new BusinessException("Frequência já registrada para este aluno nesta aula.");
        }
        Lesson lesson = lessonService.findEntityById(req.lessonId());
        Student student = studentService.findEntityById(req.studentId());

        Attendance a = Attendance.builder()
                .lesson(lesson).student(student)
                .status(req.status()).observation(req.observation()).build();
        return AttendanceResponseDTO.from(repository.save(a));
    }

    @Transactional
    public List<AttendanceResponseDTO> registerBatch(AttendanceBatchRequestDTO req) {
        Lesson lesson = lessonService.findEntityById(req.lessonId());
        List<AttendanceResponseDTO> results = new ArrayList<>();

        for (AttendanceRecordDTO record : req.records()) {
            Student student = studentService.findEntityById(record.studentId());

            Attendance a;
            if (repository.existsByLessonIdAndStudentId(lesson.getId(), student.getId())) {
                a = repository.findByLessonIdOrderByStudentCandidateFullNameAsc(lesson.getId())
                        .stream().filter(att -> att.getStudent().getId().equals(student.getId()))
                        .findFirst().orElseThrow();
                a.setStatus(record.status());
                a.setObservation(record.observation());
            } else {
                a = Attendance.builder()
                        .lesson(lesson).student(student)
                        .status(record.status()).observation(record.observation()).build();
            }
            results.add(AttendanceResponseDTO.from(repository.save(a)));
        }
        return results;
    }

    @Transactional(readOnly = true)
    public List<Object[]> getClassSummary(UUID classId) {
        return List.of();
    }
}
