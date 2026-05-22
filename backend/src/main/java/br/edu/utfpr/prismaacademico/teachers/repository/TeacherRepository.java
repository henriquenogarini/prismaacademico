package br.edu.utfpr.prismaacademico.teachers.repository;

import br.edu.utfpr.prismaacademico.teachers.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    List<Teacher> findAllByOrderByCreatedAtDesc();
    List<Teacher> findByActiveTrueOrderByCreatedAtDesc();
    long countByActive(boolean active);
}

