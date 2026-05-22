package br.edu.utfpr.prismaacademico.students.repository;

import br.edu.utfpr.prismaacademico.students.entity.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, UUID> {

    List<StudentClass> findByClassGroupId(UUID classGroupId);

    List<StudentClass> findByStudentId(UUID studentId);

    boolean existsByStudentIdAndClassGroupId(UUID studentId, UUID classGroupId);

    void deleteByStudentIdAndClassGroupId(UUID studentId, UUID classGroupId);

    @Query("SELECT COUNT(sc) FROM StudentClass sc WHERE sc.classGroup.id = :classGroupId")
    long countByClassGroupId(@Param("classGroupId") UUID classGroupId);
}

