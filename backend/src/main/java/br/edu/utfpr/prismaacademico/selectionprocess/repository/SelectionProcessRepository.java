package br.edu.utfpr.prismaacademico.selectionprocess.repository;

import br.edu.utfpr.prismaacademico.selectionprocess.entity.SelectionProcess;
import br.edu.utfpr.prismaacademico.selectionprocess.enums.SelectionProcessStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SelectionProcessRepository extends JpaRepository<SelectionProcess, UUID> {

    List<SelectionProcess> findAllByOrderByYearDescSemesterDesc();

    List<SelectionProcess> findByStatusOrderByYearDescSemesterDesc(SelectionProcessStatus status);

    Optional<SelectionProcess> findFirstByStatusOrderByYearDescSemesterDesc(SelectionProcessStatus status);
}

