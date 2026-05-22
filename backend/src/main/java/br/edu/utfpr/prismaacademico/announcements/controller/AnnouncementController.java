package br.edu.utfpr.prismaacademico.announcements.controller;

import br.edu.utfpr.prismaacademico.announcements.dto.AnnouncementRequestDTO;
import br.edu.utfpr.prismaacademico.announcements.dto.AnnouncementResponseDTO;
import br.edu.utfpr.prismaacademico.announcements.service.AnnouncementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
@Tag(name = "Comunicados", description = "Gerenciamento de comunicados")
public class AnnouncementController {

    private final AnnouncementService service;

    @GetMapping
    public ResponseEntity<List<AnnouncementResponseDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDTO> findById(@PathVariable UUID id) { return ResponseEntity.ok(service.findById(id)); }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<AnnouncementResponseDTO>> findByClass(@PathVariable UUID classId) {
        return ResponseEntity.ok(service.findByClass(classId));
    }

    @PostMapping
    public ResponseEntity<AnnouncementResponseDTO> create(@Valid @RequestBody AnnouncementRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody AnnouncementRequestDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

