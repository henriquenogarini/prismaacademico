package br.edu.utfpr.prismaacademico.selectionprocess.controller;

import br.edu.utfpr.prismaacademico.selectionprocess.dto.SelectionProcessRequestDTO;
import br.edu.utfpr.prismaacademico.selectionprocess.dto.SelectionProcessResponseDTO;
import br.edu.utfpr.prismaacademico.selectionprocess.enums.SelectionProcessStatus;
import br.edu.utfpr.prismaacademico.selectionprocess.service.SelectionProcessService;
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
@RequestMapping("/api/selection-processes")
@RequiredArgsConstructor
@Tag(name = "Processos Seletivos", description = "Gerenciamento de processos seletivos")
public class SelectionProcessController {

    private final SelectionProcessService service;

    @GetMapping
    @Operation(summary = "Listar todos os processos seletivos")
    public ResponseEntity<List<SelectionProcessResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/open")
    @Operation(summary = "Listar processos seletivos abertos")
    public ResponseEntity<List<SelectionProcessResponseDTO>> findOpen() {
        return ResponseEntity.ok(service.findOpen());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar processo seletivo por ID")
    public ResponseEntity<SelectionProcessResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Criar processo seletivo")
    public ResponseEntity<SelectionProcessResponseDTO> create(@Valid @RequestBody SelectionProcessRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar processo seletivo")
    public ResponseEntity<SelectionProcessResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody SelectionProcessRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do processo seletivo")
    public ResponseEntity<SelectionProcessResponseDTO> updateStatus(
            @PathVariable UUID id,
            @RequestBody Map<String, String> body) {
        SelectionProcessStatus status = SelectionProcessStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(service.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir processo seletivo")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

