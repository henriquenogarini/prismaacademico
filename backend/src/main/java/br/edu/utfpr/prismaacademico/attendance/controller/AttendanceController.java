package br.edu.utfpr.prismaacademico.attendance.controller;

import br.edu.utfpr.prismaacademico.attendance.dto.AttendanceBatchRequestDTO;
import br.edu.utfpr.prismaacademico.attendance.dto.AttendanceRequestDTO;
import br.edu.utfpr.prismaacademico.attendance.dto.AttendanceResponseDTO;
import br.edu.utfpr.prismaacademico.attendance.service.AttendanceService;
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
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Tag(name = "Frequência", description = "Gerenciamento de frequência")
public class AttendanceController {

    private final AttendanceService service;

    @GetMapping
    @Operation(summary = "Listar frequências")
    public ResponseEntity<List<AttendanceResponseDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/lesson/{lessonId}")
    @Operation(summary = "Frequência por aula")
    public ResponseEntity<List<AttendanceResponseDTO>> findByLesson(@PathVariable UUID lessonId) {
        return ResponseEntity.ok(service.findByLesson(lessonId));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Frequência por aluno")
    public ResponseEntity<List<AttendanceResponseDTO>> findByStudent(@PathVariable UUID studentId) {
        return ResponseEntity.ok(service.findByStudent(studentId));
    }

    @PostMapping
    @Operation(summary = "Registrar frequência individual")
    public ResponseEntity<AttendanceResponseDTO> register(@Valid @RequestBody AttendanceRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(req));
    }

    @PostMapping("/batch")
    @Operation(summary = "Registrar frequência em lote")
    public ResponseEntity<List<AttendanceResponseDTO>> registerBatch(@Valid @RequestBody AttendanceBatchRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerBatch(req));
    }

    @GetMapping("/class/{classId}/summary")
    @Operation(summary = "Resumo de frequência por turma")
    public ResponseEntity<List<Object[]>> getClassSummary(@PathVariable UUID classId) {
        return ResponseEntity.ok(service.getClassSummary(classId));
    }
}

