package br.edu.utfpr.prismaacademico.subjects.service;

import br.edu.utfpr.prismaacademico.common.exception.ResourceNotFoundException;
import br.edu.utfpr.prismaacademico.subjects.dto.SubjectRequestDTO;
import br.edu.utfpr.prismaacademico.subjects.dto.SubjectResponseDTO;
import br.edu.utfpr.prismaacademico.subjects.entity.Subject;
import br.edu.utfpr.prismaacademico.subjects.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository repository;

    @Transactional(readOnly = true)
    public List<SubjectResponseDTO> findAll() {
        return repository.findAllByOrderByNameAsc().stream().map(SubjectResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public SubjectResponseDTO findById(UUID id) {
        return SubjectResponseDTO.from(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public Subject findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Disciplina", id));
    }

    @Transactional
    public SubjectResponseDTO create(SubjectRequestDTO req) {
        Subject s = Subject.builder().name(req.name()).area(req.area())
                .active(req.active() != null ? req.active() : true).build();
        return SubjectResponseDTO.from(repository.save(s));
    }

    @Transactional
    public SubjectResponseDTO update(UUID id, SubjectRequestDTO req) {
        Subject s = findEntityById(id);
        s.setName(req.name());
        s.setArea(req.area());
        if (req.active() != null) s.setActive(req.active());
        return SubjectResponseDTO.from(repository.save(s));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }
}

