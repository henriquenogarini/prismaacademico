package br.edu.utfpr.prismaacademico.selectionprocess.service;

import br.edu.utfpr.prismaacademico.common.exception.ResourceNotFoundException;
import br.edu.utfpr.prismaacademico.selectionprocess.dto.SelectionProcessRequestDTO;
import br.edu.utfpr.prismaacademico.selectionprocess.dto.SelectionProcessResponseDTO;
import br.edu.utfpr.prismaacademico.selectionprocess.entity.SelectionProcess;
import br.edu.utfpr.prismaacademico.selectionprocess.enums.SelectionProcessStatus;
import br.edu.utfpr.prismaacademico.selectionprocess.repository.SelectionProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SelectionProcessService {

    private final SelectionProcessRepository repository;

    @Transactional(readOnly = true)
    public List<SelectionProcessResponseDTO> findAll() {
        return repository.findAllByOrderByYearDescSemesterDesc()
                .stream().map(SelectionProcessResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public SelectionProcessResponseDTO findById(UUID id) {
        return repository.findById(id)
                .map(SelectionProcessResponseDTO::from)
                .orElseThrow(() -> new ResourceNotFoundException("Processo Seletivo", id));
    }

    @Transactional(readOnly = true)
    public SelectionProcess findEntityById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Processo Seletivo", id));
    }

    @Transactional(readOnly = true)
    public List<SelectionProcessResponseDTO> findOpen() {
        return repository.findByStatusOrderByYearDescSemesterDesc(SelectionProcessStatus.OPEN)
                .stream().map(SelectionProcessResponseDTO::from).toList();
    }

    @Transactional
    public SelectionProcessResponseDTO create(SelectionProcessRequestDTO request) {
        SelectionProcess sp = SelectionProcess.builder()
                .title(request.title())
                .year(request.year())
                .semester(request.semester())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .vacancies(request.vacancies())
                .description(request.description())
                .status(request.status() != null ? request.status() : SelectionProcessStatus.DRAFT)
                .build();
        return SelectionProcessResponseDTO.from(repository.save(sp));
    }

    @Transactional
    public SelectionProcessResponseDTO update(UUID id, SelectionProcessRequestDTO request) {
        SelectionProcess sp = findEntityById(id);
        sp.setTitle(request.title());
        sp.setYear(request.year());
        sp.setSemester(request.semester());
        sp.setStartDate(request.startDate());
        sp.setEndDate(request.endDate());
        sp.setVacancies(request.vacancies());
        sp.setDescription(request.description());
        if (request.status() != null) sp.setStatus(request.status());
        return SelectionProcessResponseDTO.from(repository.save(sp));
    }

    @Transactional
    public SelectionProcessResponseDTO updateStatus(UUID id, SelectionProcessStatus status) {
        SelectionProcess sp = findEntityById(id);
        sp.setStatus(status);
        return SelectionProcessResponseDTO.from(repository.save(sp));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }
}

