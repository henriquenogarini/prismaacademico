package br.edu.utfpr.prismaacademico.reports.controller;

import br.edu.utfpr.prismaacademico.reports.dto.*;
import br.edu.utfpr.prismaacademico.reports.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "Relatórios e indicadores do sistema")
public class ReportController {

    private final ReportService service;

    @GetMapping("/overview")
    @Operation(summary = "Visão geral com indicadores gerais")
    public ResponseEntity<ReportOverviewDTO> getOverview() {
        return ResponseEntity.ok(service.getOverview());
    }

    @GetMapping("/applications-by-status")
    @Operation(summary = "Inscrições agrupadas por status")
    public ResponseEntity<List<ApplicationsByStatusDTO>> getApplicationsByStatus() {
        return ResponseEntity.ok(service.getApplicationsByStatus());
    }

    @GetMapping("/attendance-by-class")
    @Operation(summary = "Frequência média por turma")
    public ResponseEntity<List<AttendanceByClassDTO>> getAttendanceByClass() {
        return ResponseEntity.ok(service.getAttendanceByClass());
    }

    @GetMapping("/students-by-school")
    @Operation(summary = "Quantidade de candidatos por escola")
    public ResponseEntity<List<StudentsBySchoolDTO>> getStudentsBySchool() {
        return ResponseEntity.ok(service.getStudentsBySchool());
    }

    @GetMapping("/extension-summary")
    @Operation(summary = "Resumo do impacto da extensão universitária")
    public ResponseEntity<ExtensionSummaryDTO> getExtensionSummary() {
        return ResponseEntity.ok(service.getExtensionSummary());
    }
}

