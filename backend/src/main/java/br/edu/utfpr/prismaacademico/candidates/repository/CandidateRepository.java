package br.edu.utfpr.prismaacademico.candidates.repository;

import br.edu.utfpr.prismaacademico.candidates.entity.Candidate;
import br.edu.utfpr.prismaacademico.candidates.enums.CandidateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    List<Candidate> findAllByOrderByCreatedAtDesc();

    List<Candidate> findBySelectionProcessIdOrderByFullNameAsc(UUID selectionProcessId);

    List<Candidate> findByStatusOrderByCreatedAtDesc(CandidateStatus status);

    Optional<Candidate> findByDocumentNumber(String documentNumber);

    boolean existsBySelectionProcessIdAndDocumentNumber(UUID selectionProcessId, String documentNumber);

    long countByStatus(CandidateStatus status);

    @Query("SELECT c.status, COUNT(c) FROM Candidate c GROUP BY c.status")
    List<Object[]> countGroupedByStatus();

    @Query("SELECT c.schoolName, COUNT(c) FROM Candidate c WHERE c.schoolName IS NOT NULL GROUP BY c.schoolName ORDER BY COUNT(c) DESC")
    List<Object[]> countGroupedBySchool();
}

