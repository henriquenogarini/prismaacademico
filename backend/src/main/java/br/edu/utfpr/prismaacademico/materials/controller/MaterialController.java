package br.edu.utfpr.prismaacademico.materials.controller;

import br.edu.utfpr.prismaacademico.materials.dto.MaterialRequestDTO;
import br.edu.utfpr.prismaacademico.materials.dto.MaterialResponseDTO;
import br.edu.utfpr.prismaacademico.materials.service.MaterialService;
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
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@Tag(name = "Materiais", description = "Gerenciamento de materiais didáticos")
public class MaterialController {

    private final MaterialService service;

    @GetMapping
    public ResponseEntity<List<MaterialResponseDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> findById(@PathVariable UUID id) { return ResponseEntity.ok(service.findById(id)); }

    @GetMapping("/subject/{subjectId}")
    @Operation(summary = "Listar materiais por disciplina")
    public ResponseEntity<List<MaterialResponseDTO>> findBySubject(@PathVariable UUID subjectId) {
        return ResponseEntity.ok(service.findBySubject(subjectId));
    }

    @PostMapping
    public ResponseEntity<MaterialResponseDTO> create(@Valid @RequestBody MaterialRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody MaterialRequestDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

