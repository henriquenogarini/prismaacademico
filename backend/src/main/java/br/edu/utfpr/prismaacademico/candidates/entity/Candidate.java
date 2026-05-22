package br.edu.utfpr.prismaacademico.candidates.entity;

import br.edu.utfpr.prismaacademico.candidates.enums.CandidateStatus;
import br.edu.utfpr.prismaacademico.selectionprocess.entity.SelectionProcess;
import br.edu.utfpr.prismaacademico.users.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "candidates",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_candidate_process_document",
                columnNames = {"selection_process_id", "document_number"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selection_process_id", nullable = false)
    private SelectionProcess selectionProcess;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(name = "document_number", nullable = false, length = 20)
    private String documentNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "school_name", length = 200)
    private String schoolName;

    @Column(name = "school_year", length = 50)
    private String schoolYear;

    @Column(name = "public_school")
    private Boolean publicSchool;

    @Column(name = "income_per_capita", precision = 10, scale = 2)
    private BigDecimal incomePerCapita;

    @Column(name = "observation", columnDefinition = "TEXT")
    private String observation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private CandidateStatus status = CandidateStatus.SUBMITTED;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

