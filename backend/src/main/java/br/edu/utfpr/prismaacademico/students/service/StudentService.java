package br.edu.utfpr.prismaacademico.students.service;

import br.edu.utfpr.prismaacademico.attendance.repository.AttendanceRepository;
import br.edu.utfpr.prismaacademico.common.exception.ResourceNotFoundException;
import br.edu.utfpr.prismaacademico.students.dto.StudentResponseDTO;
import br.edu.utfpr.prismaacademico.students.entity.Student;
import br.edu.utfpr.prismaacademico.students.enums.StudentStatus;
import br.edu.utfpr.prismaacademico.students.repository.StudentClassRepository;
import br.edu.utfpr.prismaacademico.students.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentClassRepository studentClassRepository;
    private final AttendanceRepository attendanceRepository;

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> findAll() {
        return studentRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO findById(UUID id) {
        return toDto(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public Student findEntityById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", id));
    }

    @Transactional
    public StudentResponseDTO updateStatus(UUID id, StudentStatus status) {
        Student student = findEntityById(id);
        student.setStatus(status);
        return toDto(studentRepository.save(student));
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getOverview(UUID id) {
        Student student = findEntityById(id);
        String className = studentClassRepository.findByStudentId(id).stream()
                .findFirst().map(sc -> sc.getClassGroup().getName()).orElse(null);

        long total = attendanceRepository.countByStudentId(id);
        Double attendancePct = null;
        if (total > 0) {
            long present = attendanceRepository.countByStudentIdAndStatusPresent(id);
            attendancePct = Math.round((present * 100.0 / total) * 10.0) / 10.0;
        }

        return StudentResponseDTO.from(student, className, attendancePct);
    }

    private StudentResponseDTO toDto(Student student) {
        String className = studentClassRepository.findByStudentId(student.getId()).stream()
                .findFirst().map(sc -> sc.getClassGroup().getName()).orElse(null);
        return StudentResponseDTO.from(student, className, null);
    }
}
