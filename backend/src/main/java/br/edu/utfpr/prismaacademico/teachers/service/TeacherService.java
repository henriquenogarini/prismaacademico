package br.edu.utfpr.prismaacademico.teachers.service;

import br.edu.utfpr.prismaacademico.common.exception.ResourceNotFoundException;
import br.edu.utfpr.prismaacademico.teachers.dto.TeacherRequestDTO;
import br.edu.utfpr.prismaacademico.teachers.dto.TeacherResponseDTO;
import br.edu.utfpr.prismaacademico.teachers.entity.Teacher;
import br.edu.utfpr.prismaacademico.teachers.repository.TeacherRepository;
import br.edu.utfpr.prismaacademico.users.entity.User;
import br.edu.utfpr.prismaacademico.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository repository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<TeacherResponseDTO> findAll() {
        return repository.findAllByOrderByCreatedAtDesc().stream().map(TeacherResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public TeacherResponseDTO findById(UUID id) {
        return TeacherResponseDTO.from(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public Teacher findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Professor", id));
    }

    @Transactional
    public TeacherResponseDTO create(TeacherRequestDTO req) {
        User user = userService.findEntityById(req.userId());
        Teacher t = Teacher.builder().user(user).course(req.course()).institution(req.institution())
                .active(req.active() != null ? req.active() : true).build();
        return TeacherResponseDTO.from(repository.save(t));
    }

    @Transactional
    public TeacherResponseDTO update(UUID id, TeacherRequestDTO req) {
        Teacher t = findEntityById(id);
        User user = userService.findEntityById(req.userId());
        t.setUser(user);
        t.setCourse(req.course());
        t.setInstitution(req.institution());
        if (req.active() != null) t.setActive(req.active());
        return TeacherResponseDTO.from(repository.save(t));
    }

    @Transactional
    public TeacherResponseDTO toggleStatus(UUID id) {
        Teacher t = findEntityById(id);
        t.setActive(!t.isActive());
        return TeacherResponseDTO.from(repository.save(t));
    }
}

