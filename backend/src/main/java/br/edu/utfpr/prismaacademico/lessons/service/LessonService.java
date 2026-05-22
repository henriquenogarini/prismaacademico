package br.edu.utfpr.prismaacademico.lessons.service;

import br.edu.utfpr.prismaacademico.classgroups.service.ClassGroupService;
import br.edu.utfpr.prismaacademico.common.exception.ResourceNotFoundException;
import br.edu.utfpr.prismaacademico.lessons.dto.LessonRequestDTO;
import br.edu.utfpr.prismaacademico.lessons.dto.LessonResponseDTO;
import br.edu.utfpr.prismaacademico.lessons.entity.Lesson;
import br.edu.utfpr.prismaacademico.lessons.repository.LessonRepository;
import br.edu.utfpr.prismaacademico.subjects.service.SubjectService;
import br.edu.utfpr.prismaacademico.teachers.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository repository;
    private final ClassGroupService classGroupService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;

    @Transactional(readOnly = true)
    public List<LessonResponseDTO> findAll() {
        return repository.findAllByOrderByLessonDateDesc().stream().map(LessonResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public LessonResponseDTO findById(UUID id) {
        return LessonResponseDTO.from(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public Lesson findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Aula", id));
    }

    @Transactional(readOnly = true)
    public List<LessonResponseDTO> findByClass(UUID classId) {
        return repository.findByClassGroupIdOrderByLessonDateDesc(classId).stream().map(LessonResponseDTO::from).toList();
    }

    @Transactional
    public LessonResponseDTO create(LessonRequestDTO req) {
        Lesson l = Lesson.builder()
                .classGroup(classGroupService.findEntityById(req.classGroupId()))
                .subject(subjectService.findEntityById(req.subjectId()))
                .teacher(req.teacherId() != null ? teacherService.findEntityById(req.teacherId()) : null)
                .title(req.title())
                .lessonDate(req.lessonDate())
                .startTime(req.startTime())
                .endTime(req.endTime())
                .build();
        return LessonResponseDTO.from(repository.save(l));
    }

    @Transactional
    public LessonResponseDTO update(UUID id, LessonRequestDTO req) {
        Lesson l = findEntityById(id);
        l.setClassGroup(classGroupService.findEntityById(req.classGroupId()));
        l.setSubject(subjectService.findEntityById(req.subjectId()));
        l.setTeacher(req.teacherId() != null ? teacherService.findEntityById(req.teacherId()) : null);
        l.setTitle(req.title());
        l.setLessonDate(req.lessonDate());
        l.setStartTime(req.startTime());
        l.setEndTime(req.endTime());
        return LessonResponseDTO.from(repository.save(l));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }
}

