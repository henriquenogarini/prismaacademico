package br.edu.utfpr.prismaacademico.teachers.controller;

import br.edu.utfpr.prismaacademico.teachers.dto.TeacherRequestDTO;
import br.edu.utfpr.prismaacademico.teachers.dto.TeacherResponseDTO;
import br.edu.utfpr.prismaacademico.teachers.service.TeacherService;
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
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@Tag(name = "Professores", description = "Gerenciamento de professores voluntários")
public class TeacherController {

    private final TeacherService service;

    @GetMapping
    @Operation(summary = "Listar professores")
    public ResponseEntity<List<TeacherResponseDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar professor por ID")
    public ResponseEntity<TeacherResponseDTO> findById(@PathVariable UUID id) { return ResponseEntity.ok(service.findById(id)); }

    @PostMapping
    @Operation(summary = "Cadastrar professor")
    public ResponseEntity<TeacherResponseDTO> create(@Valid @RequestBody TeacherRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar professor")
    public ResponseEntity<TeacherResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody TeacherRequestDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Alternar status ativo do professor")
    public ResponseEntity<TeacherResponseDTO> toggleStatus(@PathVariable UUID id) {
        return ResponseEntity.ok(service.toggleStatus(id));
    }
}

