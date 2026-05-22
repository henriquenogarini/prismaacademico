package br.edu.utfpr.prismaacademico.classgroups.service;

import br.edu.utfpr.prismaacademico.classgroups.dto.ClassGroupRequestDTO;
import br.edu.utfpr.prismaacademico.classgroups.dto.ClassGroupResponseDTO;
import br.edu.utfpr.prismaacademico.classgroups.entity.ClassGroup;
import br.edu.utfpr.prismaacademico.classgroups.enums.ClassStatus;
import br.edu.utfpr.prismaacademico.classgroups.repository.ClassGroupRepository;
import br.edu.utfpr.prismaacademico.common.exception.BusinessException;
import br.edu.utfpr.prismaacademico.common.exception.ResourceNotFoundException;
import br.edu.utfpr.prismaacademico.students.dto.StudentResponseDTO;
import br.edu.utfpr.prismaacademico.students.entity.Student;
import br.edu.utfpr.prismaacademico.students.entity.StudentClass;
import br.edu.utfpr.prismaacademico.students.repository.StudentClassRepository;
import br.edu.utfpr.prismaacademico.students.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassGroupService {

    private final ClassGroupRepository classGroupRepository;
    private final StudentClassRepository studentClassRepository;
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public List<ClassGroupResponseDTO> findAll() {
        return classGroupRepository.findAllByOrderByYearDescSemesterDescNameAsc()
                .stream()
                .map(cg -> ClassGroupResponseDTO.from(cg, studentClassRepository.countByClassGroupId(cg.getId())))
                .toList();
    }

    @Transactional(readOnly = true)
    public ClassGroupResponseDTO findById(UUID id) {
        ClassGroup cg = findEntityById(id);
        return ClassGroupResponseDTO.from(cg, studentClassRepository.countByClassGroupId(id));
    }

    @Transactional(readOnly = true)
    public ClassGroup findEntityById(UUID id) {
        return classGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma", id));
    }

    @Transactional
    public ClassGroupResponseDTO create(ClassGroupRequestDTO request) {
        ClassGroup cg = ClassGroup.builder()
                .name(request.name())
                .year(request.year())
                .semester(request.semester())
                .shift(request.shift())
                .status(request.status() != null ? request.status() : ClassStatus.ACTIVE)
                .build();
        return ClassGroupResponseDTO.from(classGroupRepository.save(cg));
    }

    @Transactional
    public ClassGroupResponseDTO update(UUID id, ClassGroupRequestDTO request) {
        ClassGroup cg = findEntityById(id);
        cg.setName(request.name());
        cg.setYear(request.year());
        cg.setSemester(request.semester());
        cg.setShift(request.shift());
        if (request.status() != null) cg.setStatus(request.status());
        return ClassGroupResponseDTO.from(classGroupRepository.save(cg));
    }

    @Transactional
    public void delete(UUID id) {
        classGroupRepository.delete(findEntityById(id));
    }

    @Transactional
    public void addStudent(UUID classId, UUID studentId) {
        ClassGroup cg = findEntityById(classId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", studentId));

        if (studentClassRepository.existsByStudentIdAndClassGroupId(studentId, classId)) {
            throw new BusinessException("Aluno já está matriculado nesta turma.");
        }

        studentClassRepository.save(StudentClass.builder().student(student).classGroup(cg).build());
    }

    @Transactional
    public void removeStudent(UUID classId, UUID studentId) {
        findEntityById(classId);
        studentClassRepository.deleteByStudentIdAndClassGroupId(studentId, classId);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getStudents(UUID classId) {
        findEntityById(classId);
        return studentClassRepository.findByClassGroupId(classId)
                .stream()
                .map(sc -> StudentResponseDTO.from(sc.getStudent()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ClassGroupResponseDTO getOverview(UUID id) {
        return findById(id);
    }
}
