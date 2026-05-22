package br.edu.utfpr.prismaacademico.candidates.controller;

import br.edu.utfpr.prismaacademico.candidates.dto.*;
import br.edu.utfpr.prismaacademico.candidates.enums.CandidateStatus;
import br.edu.utfpr.prismaacademico.candidates.enums.DocumentStatus;
import br.edu.utfpr.prismaacademico.candidates.service.CandidateService;
import br.edu.utfpr.prismaacademico.students.dto.StudentResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
@Tag(name = "Candidatos", description = "Gerenciamento de candidatos e inscrições")
public class CandidateController {

    private final CandidateService service;

    @GetMapping
    @Operation(summary = "Listar candidatos")
    public ResponseEntity<List<CandidateResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar candidato por ID")
    public ResponseEntity<CandidateResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/status/{documentNumber}")
    @Operation(summary = "Consultar status da inscrição pelo número do documento (público)")
    public ResponseEntity<CandidateResponseDTO> findByDocument(@PathVariable String documentNumber) {
        return ResponseEntity.ok(service.findByDocumentNumber(documentNumber));
    }

    @PostMapping
    @Operation(summary = "Cadastrar candidato (administrativo)")
    public ResponseEntity<CandidateResponseDTO> create(@Valid @RequestBody CandidateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PostMapping("/public-application")
    @Operation(summary = "Inscrição pública de candidato (sem autenticação)")
    public ResponseEntity<CandidateResponseDTO> publicApplication(@Valid @RequestBody CandidateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.publicApplication(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar candidato")
    public ResponseEntity<CandidateResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody CandidateRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do candidato")
    public ResponseEntity<CandidateResponseDTO> updateStatus(
            @PathVariable UUID id,
            @RequestBody UpdateCandidateStatusDTO request) {
        return ResponseEntity.ok(service.updateStatus(id, request));
    }

    @PostMapping("/{id}/approve")
    @Operation(summary = "Aprovar candidato")
    public ResponseEntity<CandidateResponseDTO> approve(@PathVariable UUID id) {
        return ResponseEntity.ok(service.approve(id));
    }

    @PostMapping("/{id}/reject")
    @Operation(summary = "Reprovar candidato")
    public ResponseEntity<CandidateResponseDTO> reject(@PathVariable UUID id) {
        return ResponseEntity.ok(service.reject(id));
    }

    @PostMapping("/{id}/mark-pending")
    @Operation(summary = "Marcar candidato como pendente")
    public ResponseEntity<CandidateResponseDTO> markPending(@PathVariable UUID id) {
        return ResponseEntity.ok(service.markPending(id));
    }

    @PostMapping("/{id}/convert-to-student")
    @Operation(summary = "Converter candidato aprovado em aluno")
    public ResponseEntity<StudentResponseDTO> convertToStudent(
            @PathVariable UUID id,
            @RequestBody(required = false) ConvertCandidateToStudentDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.convertToStudent(id, request));
    }

    @GetMapping("/{candidateId}/documents")
    @Operation(summary = "Listar documentos do candidato")
    public ResponseEntity<List<CandidateDocumentResponseDTO>> getDocuments(@PathVariable UUID candidateId) {
        return ResponseEntity.ok(service.findDocuments(candidateId));
    }

    @PostMapping("/{candidateId}/documents")
    @Operation(summary = "Adicionar documento ao candidato")
    public ResponseEntity<CandidateDocumentResponseDTO> addDocument(
            @PathVariable UUID candidateId,
            @Valid @RequestBody CandidateDocumentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addDocument(candidateId, request));
    }

    @PatchMapping("/documents/{id}/status")
    @Operation(summary = "Atualizar status de documento")
    public ResponseEntity<CandidateDocumentResponseDTO> updateDocumentStatus(
            @PathVariable UUID id,
            @RequestBody Map<String, String> body) {
        DocumentStatus status = DocumentStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(service.updateDocumentStatus(id, status));
    }
}

