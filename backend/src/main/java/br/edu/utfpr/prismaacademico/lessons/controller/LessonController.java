package br.edu.utfpr.prismaacademico.lessons.controller;

import br.edu.utfpr.prismaacademico.lessons.dto.LessonRequestDTO;
import br.edu.utfpr.prismaacademico.lessons.dto.LessonResponseDTO;
import br.edu.utfpr.prismaacademico.lessons.service.LessonService;
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
@Tag(name = "Aulas", description = "Gerenciamento de aulas")
public class LessonController {

    private final LessonService service;

    @GetMapping("/api/lessons")
    @Operation(summary = "Listar aulas")
    public ResponseEntity<List<LessonResponseDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/api/lessons/{id}")
    @Operation(summary = "Buscar aula por ID")
    public ResponseEntity<LessonResponseDTO> findById(@PathVariable UUID id) { return ResponseEntity.ok(service.findById(id)); }

    @GetMapping("/api/classes/{classId}/lessons")
    @Operation(summary = "Listar aulas por turma")
    public ResponseEntity<List<LessonResponseDTO>> findByClass(@PathVariable UUID classId) {
        return ResponseEntity.ok(service.findByClass(classId));
    }

    @PostMapping("/api/lessons")
    @Operation(summary = "Criar aula")
    public ResponseEntity<LessonResponseDTO> create(@Valid @RequestBody LessonRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/api/lessons/{id}")
    @Operation(summary = "Atualizar aula")
    public ResponseEntity<LessonResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody LessonRequestDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/api/lessons/{id}")
    @Operation(summary = "Excluir aula")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

