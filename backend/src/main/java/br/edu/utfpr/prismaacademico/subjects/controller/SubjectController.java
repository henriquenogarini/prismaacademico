package br.edu.utfpr.prismaacademico.subjects.controller;

import br.edu.utfpr.prismaacademico.subjects.dto.SubjectRequestDTO;
import br.edu.utfpr.prismaacademico.subjects.dto.SubjectResponseDTO;
import br.edu.utfpr.prismaacademico.subjects.service.SubjectService;
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
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
@Tag(name = "Disciplinas", description = "Gerenciamento de disciplinas")
public class SubjectController {

    private final SubjectService service;

    @GetMapping
    @Operation(summary = "Listar disciplinas")
    public ResponseEntity<List<SubjectResponseDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar disciplina por ID")
    public ResponseEntity<SubjectResponseDTO> findById(@PathVariable UUID id) { return ResponseEntity.ok(service.findById(id)); }

    @PostMapping
    @Operation(summary = "Criar disciplina")
    public ResponseEntity<SubjectResponseDTO> create(@Valid @RequestBody SubjectRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar disciplina")
    public ResponseEntity<SubjectResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody SubjectRequestDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir disciplina")
    public ResponseEntity<Void> delete(@PathVariable UUID id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

