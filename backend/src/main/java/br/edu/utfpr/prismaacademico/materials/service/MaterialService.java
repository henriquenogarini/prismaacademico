package br.edu.utfpr.prismaacademico.materials.service;

import br.edu.utfpr.prismaacademico.common.exception.ResourceNotFoundException;
import br.edu.utfpr.prismaacademico.materials.dto.MaterialRequestDTO;
import br.edu.utfpr.prismaacademico.materials.dto.MaterialResponseDTO;
import br.edu.utfpr.prismaacademico.materials.entity.Material;
import br.edu.utfpr.prismaacademico.materials.repository.MaterialRepository;
import br.edu.utfpr.prismaacademico.subjects.service.SubjectService;
import br.edu.utfpr.prismaacademico.teachers.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository repository;
    private final SubjectService subjectService;
    private final TeacherService teacherService;

    @Transactional(readOnly = true)
    public List<MaterialResponseDTO> findAll() {
        return repository.findAllByOrderByCreatedAtDesc().stream().map(MaterialResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public MaterialResponseDTO findById(UUID id) {
        return MaterialResponseDTO.from(findEntityById(id));
    }

    public Material findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Material", id));
    }

    @Transactional(readOnly = true)
    public List<MaterialResponseDTO> findBySubject(UUID subjectId) {
        return repository.findBySubjectIdOrderByCreatedAtDesc(subjectId).stream().map(MaterialResponseDTO::from).toList();
    }

    @Transactional
    public MaterialResponseDTO create(MaterialRequestDTO req) {
        Material m = Material.builder()
                .subject(subjectService.findEntityById(req.subjectId()))
                .teacher(req.teacherId() != null ? teacherService.findEntityById(req.teacherId()) : null)
                .title(req.title()).description(req.description()).fileUrl(req.fileUrl()).build();
        return MaterialResponseDTO.from(repository.save(m));
    }

    @Transactional
    public MaterialResponseDTO update(UUID id, MaterialRequestDTO req) {
        Material m = findEntityById(id);
        m.setSubject(subjectService.findEntityById(req.subjectId()));
        m.setTeacher(req.teacherId() != null ? teacherService.findEntityById(req.teacherId()) : null);
        m.setTitle(req.title()); m.setDescription(req.description()); m.setFileUrl(req.fileUrl());
        return MaterialResponseDTO.from(repository.save(m));
    }

    @Transactional
    public void delete(UUID id) { repository.delete(findEntityById(id)); }
}

