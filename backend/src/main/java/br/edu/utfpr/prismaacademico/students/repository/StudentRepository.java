package br.edu.utfpr.prismaacademico.students.repository;

import br.edu.utfpr.prismaacademico.students.entity.Student;
import br.edu.utfpr.prismaacademico.students.enums.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    List<Student> findAllByOrderByCreatedAtDesc();

    boolean existsByCandidateId(UUID candidateId);

    boolean existsByRegistrationNumber(String registrationNumber);

    long countByStatus(StudentStatus status);

    List<Student> findByStatusOrderByCreatedAtDesc(StudentStatus status);
}

