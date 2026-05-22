package br.edu.utfpr.prismaacademico.candidates.service;

import br.edu.utfpr.prismaacademico.candidates.dto.*;
import br.edu.utfpr.prismaacademico.candidates.entity.Candidate;
import br.edu.utfpr.prismaacademico.candidates.entity.CandidateDocument;
import br.edu.utfpr.prismaacademico.candidates.enums.CandidateStatus;
import br.edu.utfpr.prismaacademico.candidates.enums.DocumentStatus;
import br.edu.utfpr.prismaacademico.candidates.repository.CandidateDocumentRepository;
import br.edu.utfpr.prismaacademico.candidates.repository.CandidateRepository;
import br.edu.utfpr.prismaacademico.classgroups.entity.ClassGroup;
import br.edu.utfpr.prismaacademico.classgroups.repository.ClassGroupRepository;
import br.edu.utfpr.prismaacademico.common.exception.BusinessException;
import br.edu.utfpr.prismaacademico.common.exception.ResourceNotFoundException;
import br.edu.utfpr.prismaacademico.selectionprocess.entity.SelectionProcess;
import br.edu.utfpr.prismaacademico.selectionprocess.service.SelectionProcessService;
import br.edu.utfpr.prismaacademico.students.entity.Student;
import br.edu.utfpr.prismaacademico.students.entity.StudentClass;
import br.edu.utfpr.prismaacademico.students.enums.StudentStatus;
import br.edu.utfpr.prismaacademico.students.repository.StudentClassRepository;
import br.edu.utfpr.prismaacademico.students.repository.StudentRepository;
import br.edu.utfpr.prismaacademico.students.dto.StudentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateDocumentRepository candidateDocumentRepository;
    private final SelectionProcessService selectionProcessService;
    private final StudentRepository studentRepository;
    private final StudentClassRepository studentClassRepository;
    private final ClassGroupRepository classGroupRepository;

    @Transactional(readOnly = true)
    public List<CandidateResponseDTO> findAll() {
        return candidateRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(CandidateResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public CandidateResponseDTO findById(UUID id) {
        return candidateRepository.findById(id)
                .map(CandidateResponseDTO::from)
                .orElseThrow(() -> new ResourceNotFoundException("Candidato", id));
    }

    @Transactional(readOnly = true)
    public Candidate findEntityById(UUID id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidato", id));
    }

    @Transactional(readOnly = true)
    public CandidateResponseDTO findByDocumentNumber(String documentNumber) {
        return candidateRepository.findByDocumentNumber(documentNumber)
                .map(CandidateResponseDTO::from)
                .orElseThrow(() -> new ResourceNotFoundException("Candidato com documento: " + documentNumber));
    }

    @Transactional
    public CandidateResponseDTO create(CandidateRequestDTO request) {
        SelectionProcess sp = selectionProcessService.findEntityById(request.selectionProcessId());

        if (candidateRepository.existsBySelectionProcessIdAndDocumentNumber(
                sp.getId(), request.documentNumber())) {
            throw new BusinessException("Candidato com este documento já cadastrado neste processo seletivo.");
        }

        Candidate candidate = Candidate.builder()
                .selectionProcess(sp)
                .fullName(request.fullName())
                .documentNumber(request.documentNumber())
                .birthDate(request.birthDate())
                .phone(request.phone())
                .email(request.email())
                .schoolName(request.schoolName())
                .schoolYear(request.schoolYear())
                .publicSchool(request.publicSchool())
                .incomePerCapita(request.incomePerCapita())
                .observation(request.observation())
                .status(CandidateStatus.SUBMITTED)
                .build();

        return CandidateResponseDTO.from(candidateRepository.save(candidate));
    }

    @Transactional
    public CandidateResponseDTO publicApplication(CandidateRequestDTO request) {
        return create(request);
    }

    @Transactional
    public CandidateResponseDTO update(UUID id, CandidateRequestDTO request) {
        Candidate candidate = findEntityById(id);
        SelectionProcess sp = selectionProcessService.findEntityById(request.selectionProcessId());

        candidate.setSelectionProcess(sp);
        candidate.setFullName(request.fullName());
        candidate.setDocumentNumber(request.documentNumber());
        candidate.setBirthDate(request.birthDate());
        candidate.setPhone(request.phone());
        candidate.setEmail(request.email());
        candidate.setSchoolName(request.schoolName());
        candidate.setSchoolYear(request.schoolYear());
        candidate.setPublicSchool(request.publicSchool());
        candidate.setIncomePerCapita(request.incomePerCapita());
        candidate.setObservation(request.observation());

        return CandidateResponseDTO.from(candidateRepository.save(candidate));
    }

    @Transactional
    public CandidateResponseDTO updateStatus(UUID id, UpdateCandidateStatusDTO request) {
        Candidate candidate = findEntityById(id);
        candidate.setStatus(request.status());
        if (request.observation() != null) {
            candidate.setObservation(request.observation());
        }
        return CandidateResponseDTO.from(candidateRepository.save(candidate));
    }

    @Transactional
    public CandidateResponseDTO approve(UUID id) {
        Candidate candidate = findEntityById(id);
        if (candidate.getStatus() == CandidateStatus.APPROVED) {
            throw new BusinessException("Candidato já está aprovado.");
        }
        candidate.setStatus(CandidateStatus.APPROVED);
        return CandidateResponseDTO.from(candidateRepository.save(candidate));
    }

    @Transactional
    public CandidateResponseDTO reject(UUID id) {
        Candidate candidate = findEntityById(id);
        candidate.setStatus(CandidateStatus.REJECTED);
        return CandidateResponseDTO.from(candidateRepository.save(candidate));
    }

    @Transactional
    public CandidateResponseDTO markPending(UUID id) {
        Candidate candidate = findEntityById(id);
        candidate.setStatus(CandidateStatus.PENDING);
        return CandidateResponseDTO.from(candidateRepository.save(candidate));
    }

    @Transactional
    public StudentResponseDTO convertToStudent(UUID candidateId, ConvertCandidateToStudentDTO request) {
        Candidate candidate = findEntityById(candidateId);

        if (candidate.getStatus() != CandidateStatus.APPROVED) {
            throw new BusinessException("Apenas candidatos APROVADOS podem ser convertidos em alunos.");
        }

        if (studentRepository.existsByCandidateId(candidateId)) {
            throw new BusinessException("Este candidato já foi convertido em aluno.");
        }

        String registrationNumber = generateRegistrationNumber(candidate);

        Student student = Student.builder()
                .candidate(candidate)
                .registrationNumber(registrationNumber)
                .status(StudentStatus.ACTIVE)
                .enrollmentDate(LocalDate.now())
                .build();

        student = studentRepository.save(student);

        if (request != null && request.classGroupId() != null) {
            ClassGroup classGroup = classGroupRepository.findById(request.classGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("Turma", request.classGroupId()));

            if (studentClassRepository.existsByStudentIdAndClassGroupId(student.getId(), classGroup.getId())) {
                throw new BusinessException("Aluno já está matriculado nesta turma.");
            }

            StudentClass sc = StudentClass.builder()
                    .student(student)
                    .classGroup(classGroup)
                    .build();
            studentClassRepository.save(sc);
        }

        return StudentResponseDTO.from(student);
    }

    private String generateRegistrationNumber(Candidate candidate) {
        int year = candidate.getSelectionProcess() != null
                ? candidate.getSelectionProcess().getYear()
                : Year.now().getValue();

        long count = studentRepository.count() + 1;
        String seq = String.format("%04d", count);

        String candidate_number = "PRISMA-" + year + "-" + seq;

        // Ensure uniqueness
        while (studentRepository.existsByRegistrationNumber(candidate_number)) {
            count++;
            seq = String.format("%04d", count);
            candidate_number = "PRISMA-" + year + "-" + seq;
        }

        return candidate_number;
    }

    // Documents
    @Transactional(readOnly = true)
    public List<CandidateDocumentResponseDTO> findDocuments(UUID candidateId) {
        findEntityById(candidateId); // validates existence
        return candidateDocumentRepository.findByCandidateIdOrderByUploadedAtDesc(candidateId)
                .stream().map(CandidateDocumentResponseDTO::from).toList();
    }

    @Transactional
    public CandidateDocumentResponseDTO addDocument(UUID candidateId, CandidateDocumentRequestDTO request) {
        Candidate candidate = findEntityById(candidateId);

        CandidateDocument document = CandidateDocument.builder()
                .candidate(candidate)
                .documentType(request.documentType())
                .fileUrl(request.fileUrl())
                .observation(request.observation())
                .status(DocumentStatus.SUBMITTED)
                .build();

        return CandidateDocumentResponseDTO.from(candidateDocumentRepository.save(document));
    }

    @Transactional
    public CandidateDocumentResponseDTO updateDocumentStatus(UUID documentId, DocumentStatus status) {
        CandidateDocument doc = candidateDocumentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Documento", documentId));
        doc.setStatus(status);
        return CandidateDocumentResponseDTO.from(candidateDocumentRepository.save(doc));
    }
}

