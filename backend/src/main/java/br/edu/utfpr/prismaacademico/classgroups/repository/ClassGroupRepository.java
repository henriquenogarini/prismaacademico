package br.edu.utfpr.prismaacademico.classgroups.repository;

import br.edu.utfpr.prismaacademico.classgroups.entity.ClassGroup;
import br.edu.utfpr.prismaacademico.classgroups.enums.ClassStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, UUID> {

    List<ClassGroup> findAllByOrderByYearDescSemesterDescNameAsc();

    List<ClassGroup> findByStatusOrderByNameAsc(ClassStatus status);

    long countByStatus(ClassStatus status);
}

