package br.edu.utfpr.prismaacademico.students.controller;

import br.edu.utfpr.prismaacademico.students.dto.StudentResponseDTO;
import br.edu.utfpr.prismaacademico.students.enums.StudentStatus;
import br.edu.utfpr.prismaacademico.students.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Alunos", description = "Gerenciamento de alunos matriculados")
public class StudentController {

    private final StudentService service;

    @GetMapping
    @Operation(summary = "Listar todos os alunos")
    public ResponseEntity<List<StudentResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluno por ID")
    public ResponseEntity<StudentResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/{id}/overview")
    @Operation(summary = "Visão geral do aluno (turma, frequência)")
    public ResponseEntity<StudentResponseDTO> getOverview(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOverview(id));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do aluno")
    public ResponseEntity<StudentResponseDTO> updateStatus(
            @PathVariable UUID id,
            @RequestBody Map<String, String> body) {
        StudentStatus status = StudentStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(service.updateStatus(id, status));
    }
}

