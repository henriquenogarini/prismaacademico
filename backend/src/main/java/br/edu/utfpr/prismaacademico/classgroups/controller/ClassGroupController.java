package br.edu.utfpr.prismaacademico.classgroups.controller;

import br.edu.utfpr.prismaacademico.classgroups.dto.ClassGroupRequestDTO;
import br.edu.utfpr.prismaacademico.classgroups.dto.ClassGroupResponseDTO;
import br.edu.utfpr.prismaacademico.classgroups.service.ClassGroupService;
import br.edu.utfpr.prismaacademico.students.dto.StudentResponseDTO;
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
@RequestMapping("/api/classes")
@RequiredArgsConstructor
@Tag(name = "Turmas", description = "Gerenciamento de turmas (class groups)")
public class ClassGroupController {

    private final ClassGroupService service;

    @GetMapping
    @Operation(summary = "Listar turmas")
    public ResponseEntity<List<ClassGroupResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar turma por ID")
    public ResponseEntity<ClassGroupResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/{id}/overview")
    @Operation(summary = "Visão geral da turma")
    public ResponseEntity<ClassGroupResponseDTO> getOverview(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOverview(id));
    }

    @PostMapping
    @Operation(summary = "Criar turma")
    public ResponseEntity<ClassGroupResponseDTO> create(@Valid @RequestBody ClassGroupRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar turma")
    public ResponseEntity<ClassGroupResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody ClassGroupRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir turma")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{classId}/students/{studentId}")
    @Operation(summary = "Adicionar aluno à turma")
    public ResponseEntity<Void> addStudent(
            @PathVariable UUID classId,
            @PathVariable UUID studentId) {
        service.addStudent(classId, studentId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{classId}/students/{studentId}")
    @Operation(summary = "Remover aluno da turma")
    public ResponseEntity<Void> removeStudent(
            @PathVariable UUID classId,
            @PathVariable UUID studentId) {
        service.removeStudent(classId, studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "Listar alunos da turma")
    public ResponseEntity<List<StudentResponseDTO>> getStudents(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getStudents(id));
    }
}

