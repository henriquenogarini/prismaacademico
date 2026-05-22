package br.edu.utfpr.prismaacademico.candidates;

import br.edu.utfpr.prismaacademico.candidates.dto.CandidateRequestDTO;
import br.edu.utfpr.prismaacademico.candidates.dto.CandidateResponseDTO;
import br.edu.utfpr.prismaacademico.candidates.entity.Candidate;
import br.edu.utfpr.prismaacademico.candidates.enums.CandidateStatus;
import br.edu.utfpr.prismaacademico.candidates.repository.CandidateDocumentRepository;
import br.edu.utfpr.prismaacademico.candidates.repository.CandidateRepository;
import br.edu.utfpr.prismaacademico.candidates.service.CandidateService;
import br.edu.utfpr.prismaacademico.classgroups.repository.ClassGroupRepository;
import br.edu.utfpr.prismaacademico.common.exception.BusinessException;
import br.edu.utfpr.prismaacademico.common.exception.ResourceNotFoundException;
import br.edu.utfpr.prismaacademico.selectionprocess.entity.SelectionProcess;
import br.edu.utfpr.prismaacademico.selectionprocess.enums.SelectionProcessStatus;
import br.edu.utfpr.prismaacademico.selectionprocess.service.SelectionProcessService;
import br.edu.utfpr.prismaacademico.students.repository.StudentClassRepository;
import br.edu.utfpr.prismaacademico.students.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CandidateService - Testes Unitários")
class CandidateServiceTest {

    @Mock private CandidateRepository candidateRepository;
    @Mock private CandidateDocumentRepository candidateDocumentRepository;
    @Mock private SelectionProcessService selectionProcessService;
    @Mock private StudentRepository studentRepository;
    @Mock private StudentClassRepository studentClassRepository;
    @Mock private ClassGroupRepository classGroupRepository;

    @InjectMocks
    private CandidateService candidateService;

    private SelectionProcess mockProcess;
    private Candidate mockCandidate;

    @BeforeEach
    void setUp() {
        mockProcess = SelectionProcess.builder()
                .id(UUID.randomUUID())
                .title("PS Prisma 2026/1")
                .year(2026).semester(1)
                .status(SelectionProcessStatus.OPEN)
                .build();

        mockCandidate = Candidate.builder()
                .id(UUID.randomUUID())
                .selectionProcess(mockProcess)
                .fullName("Ana Clara Santos")
                .documentNumber("111.222.333-44")
                .status(CandidateStatus.SUBMITTED)
                .build();
    }

    @Test
    @DisplayName("findAll deve retornar lista de candidatos")
    void findAll_returnsListOfCandidates() {
        when(candidateRepository.findAllByOrderByCreatedAtDesc()).thenReturn(List.of(mockCandidate));

        List<CandidateResponseDTO> result = candidateService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).fullName()).isEqualTo("Ana Clara Santos");
    }

    @Test
    @DisplayName("findById com ID válido deve retornar candidato")
    void findById_validId_returnsCandidate() {
        when(candidateRepository.findById(mockCandidate.getId())).thenReturn(Optional.of(mockCandidate));

        CandidateResponseDTO result = candidateService.findById(mockCandidate.getId());

        assertThat(result).isNotNull();
        assertThat(result.fullName()).isEqualTo("Ana Clara Santos");
    }

    @Test
    @DisplayName("findById com ID inválido deve lançar ResourceNotFoundException")
    void findById_invalidId_throwsException() {
        UUID randomId = UUID.randomUUID();
        when(candidateRepository.findById(randomId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> candidateService.findById(randomId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("create deve salvar e retornar candidato")
    void create_validRequest_savesAndReturnsCandidate() {
        var request = new CandidateRequestDTO(mockProcess.getId(), "João Pedro", "999.888.777-66",
                null, null, null, null, null, null, null, null);

        when(selectionProcessService.findEntityById(mockProcess.getId())).thenReturn(mockProcess);
        when(candidateRepository.existsBySelectionProcessIdAndDocumentNumber(any(), any())).thenReturn(false);
        when(candidateRepository.save(any())).thenReturn(mockCandidate);

        CandidateResponseDTO result = candidateService.create(request);

        assertThat(result).isNotNull();
        verify(candidateRepository, times(1)).save(any(Candidate.class));
    }

    @Test
    @DisplayName("create com documento duplicado deve lançar BusinessException")
    void create_duplicateDocument_throwsBusinessException() {
        var request = new CandidateRequestDTO(mockProcess.getId(), "Duplicado", "111.222.333-44",
                null, null, null, null, null, null, null, null);

        when(selectionProcessService.findEntityById(mockProcess.getId())).thenReturn(mockProcess);
        when(candidateRepository.existsBySelectionProcessIdAndDocumentNumber(any(), any())).thenReturn(true);

        assertThatThrownBy(() -> candidateService.create(request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("já cadastrado");
    }

    @Test
    @DisplayName("convertToStudent sem status APPROVED deve lançar BusinessException")
    void convertToStudent_notApproved_throwsBusinessException() {
        mockCandidate.setStatus(CandidateStatus.SUBMITTED);
        when(candidateRepository.findById(mockCandidate.getId())).thenReturn(Optional.of(mockCandidate));

        assertThatThrownBy(() -> candidateService.convertToStudent(mockCandidate.getId(), null))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("APROVADOS");
    }
}
