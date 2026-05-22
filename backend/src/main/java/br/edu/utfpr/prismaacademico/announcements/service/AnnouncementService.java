package br.edu.utfpr.prismaacademico.announcements.service;

import br.edu.utfpr.prismaacademico.announcements.dto.AnnouncementRequestDTO;
import br.edu.utfpr.prismaacademico.announcements.dto.AnnouncementResponseDTO;
import br.edu.utfpr.prismaacademico.announcements.entity.Announcement;
import br.edu.utfpr.prismaacademico.announcements.repository.AnnouncementRepository;
import br.edu.utfpr.prismaacademico.classgroups.repository.ClassGroupRepository;
import br.edu.utfpr.prismaacademico.common.exception.ResourceNotFoundException;
import br.edu.utfpr.prismaacademico.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository repository;
    private final UserService userService;
    private final ClassGroupRepository classGroupRepository;

    @Transactional(readOnly = true)
    public List<AnnouncementResponseDTO> findAll() {
        return repository.findAllByOrderByCreatedAtDesc().stream().map(AnnouncementResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public AnnouncementResponseDTO findById(UUID id) {
        return AnnouncementResponseDTO.from(findEntityById(id));
    }

    public Announcement findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comunicado", id));
    }

    @Transactional(readOnly = true)
    public List<AnnouncementResponseDTO> findByClass(UUID classId) {
        return repository.findByClassGroupIdOrderByCreatedAtDesc(classId)
                .stream().map(AnnouncementResponseDTO::from).toList();
    }

    @Transactional
    public AnnouncementResponseDTO create(AnnouncementRequestDTO req) {
        Announcement a = Announcement.builder()
                .author(userService.findEntityById(req.authorId()))
                .classGroup(req.classGroupId() != null
                        ? classGroupRepository.findById(req.classGroupId()).orElse(null) : null)
                .title(req.title()).content(req.content()).build();
        return AnnouncementResponseDTO.from(repository.save(a));
    }

    @Transactional
    public AnnouncementResponseDTO update(UUID id, AnnouncementRequestDTO req) {
        Announcement a = findEntityById(id);
        a.setAuthor(userService.findEntityById(req.authorId()));
        a.setClassGroup(req.classGroupId() != null
                ? classGroupRepository.findById(req.classGroupId()).orElse(null) : null);
        a.setTitle(req.title()); a.setContent(req.content());
        return AnnouncementResponseDTO.from(repository.save(a));
    }

    @Transactional
    public void delete(UUID id) { repository.delete(findEntityById(id)); }
}

