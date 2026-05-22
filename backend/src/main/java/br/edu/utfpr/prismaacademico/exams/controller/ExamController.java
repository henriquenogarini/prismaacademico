package br.edu.utfpr.prismaacademico.exams.controller;

import br.edu.utfpr.prismaacademico.exams.dto.*;
import br.edu.utfpr.prismaacademico.exams.service.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Simulados", description = "Gerenciamento de simulados e resultados")
public class ExamController {

    private final ExamService service;

    @GetMapping("/api/exams")
    public ResponseEntity<List<ExamResponseDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/api/exams/{id}")
    public ResponseEntity<ExamResponseDTO> findById(@PathVariable UUID id) { return ResponseEntity.ok(service.findById(id)); }

    @GetMapping("/api/exams/class/{classId}")
    @Operation(summary = "Simulados por turma")
    public ResponseEntity<List<ExamResponseDTO>> findByClass(@PathVariable UUID classId) {
        return ResponseEntity.ok(service.findByClass(classId));
    }

    @PostMapping("/api/exams")
    public ResponseEntity<ExamResponseDTO> create(@Valid @RequestBody ExamRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/api/exams/{id}")
    public ResponseEntity<ExamResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody ExamRequestDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/api/exams/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/exams/{examId}/results")
    @Operation(summary = "Resultados do simulado")
    public ResponseEntity<List<ExamResultResponseDTO>> findResults(@PathVariable UUID examId) {
        return ResponseEntity.ok(service.findResults(examId));
    }

    @PostMapping("/api/exams/{examId}/results")
    @Operation(summary = "Adicionar resultado individual")
    public ResponseEntity<ExamResultResponseDTO> addResult(
            @PathVariable UUID examId,
            @Valid @RequestBody ExamResultRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addResult(examId, req));
    }

    @PostMapping("/api/exams/{examId}/results/batch")
    @Operation(summary = "Adicionar resultados em lote")
    public ResponseEntity<List<ExamResultResponseDTO>> addResultBatch(
            @PathVariable UUID examId,
            @RequestBody List<ExamResultRequestDTO> results) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addResultBatch(examId, results));
    }

    @GetMapping("/api/students/{studentId}/exam-results")
    @Operation(summary = "Resultados de simulados do aluno")
    public ResponseEntity<List<ExamResultResponseDTO>> findResultsByStudent(@PathVariable UUID studentId) {
        return ResponseEntity.ok(service.findResultsByStudent(studentId));
    }
}

