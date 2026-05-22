package br.edu.utfpr.prismaacademico.reports.service;

import br.edu.utfpr.prismaacademico.attendance.repository.AttendanceRepository;
import br.edu.utfpr.prismaacademico.candidates.enums.CandidateStatus;
import br.edu.utfpr.prismaacademico.candidates.repository.CandidateRepository;
import br.edu.utfpr.prismaacademico.classgroups.enums.ClassStatus;
import br.edu.utfpr.prismaacademico.classgroups.repository.ClassGroupRepository;
import br.edu.utfpr.prismaacademico.reports.dto.*;
import br.edu.utfpr.prismaacademico.students.enums.StudentStatus;
import br.edu.utfpr.prismaacademico.students.repository.StudentRepository;
import br.edu.utfpr.prismaacademico.teachers.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final CandidateRepository candidateRepository;
    private final StudentRepository studentRepository;
    private final ClassGroupRepository classGroupRepository;
    private final TeacherRepository teacherRepository;
    private final AttendanceRepository attendanceRepository;

    private static final Map<String, String> STATUS_LABELS = Map.of(
            "SUBMITTED", "Enviada",
            "UNDER_REVIEW", "Em Análise",
            "PENDING", "Pendente",
            "APPROVED", "Aprovada",
            "REJECTED", "Reprovada"
    );

    @Transactional(readOnly = true)
    public ReportOverviewDTO getOverview() {
        long totalCandidates = candidateRepository.count();
        long approved = candidateRepository.countByStatus(CandidateStatus.APPROVED);
        long rejected = candidateRepository.countByStatus(CandidateStatus.REJECTED);
        long pending = candidateRepository.countByStatus(CandidateStatus.PENDING)
                + candidateRepository.countByStatus(CandidateStatus.SUBMITTED)
                + candidateRepository.countByStatus(CandidateStatus.UNDER_REVIEW);
        long enrolled = studentRepository.count();
        long active = studentRepository.countByStatus(StudentStatus.ACTIVE);
        long activeClasses = classGroupRepository.countByStatus(ClassStatus.ACTIVE);
        long teachers = teacherRepository.countByActive(true);
        double avgAttendance = calculateAverageAttendance();

        return new ReportOverviewDTO(totalCandidates, approved, rejected, pending,
                enrolled, active, activeClasses, teachers, avgAttendance);
    }

    @Transactional(readOnly = true)
    public List<ApplicationsByStatusDTO> getApplicationsByStatus() {
        return candidateRepository.countGroupedByStatus().stream()
                .map(r -> new ApplicationsByStatusDTO(
                        r[0].toString(),
                        STATUS_LABELS.getOrDefault(r[0].toString(), r[0].toString()),
                        ((Number) r[1]).longValue()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AttendanceByClassDTO> getAttendanceByClass() {
        return attendanceRepository.attendancePercentageByClass().stream()
                .map(r -> new AttendanceByClassDTO(
                        r[0].toString(),
                        Math.round(((Number) r[1]).doubleValue() * 10.0) / 10.0))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StudentsBySchoolDTO> getStudentsBySchool() {
        return candidateRepository.countGroupedBySchool().stream()
                .map(r -> new StudentsBySchoolDTO(
                        r[0] != null ? r[0].toString() : "Não informado",
                        ((Number) r[1]).longValue()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ExtensionSummaryDTO getExtensionSummary() {
        long totalApplicants = candidateRepository.count();
        long totalEnrolled = studentRepository.count();
        long teachers = teacherRepository.countByActive(true);
        long activeClasses = classGroupRepository.countByStatus(ClassStatus.ACTIVE);
        long schoolsServed = getStudentsBySchool().stream()
                .filter(s -> !"Não informado".equals(s.schoolName())).count();
        double avgAttendance = calculateAverageAttendance();

        return new ExtensionSummaryDTO(totalApplicants, totalEnrolled, teachers, schoolsServed, avgAttendance, activeClasses);
    }

    private double calculateAverageAttendance() {
        List<AttendanceByClassDTO> byClass = getAttendanceByClass();
        if (byClass.isEmpty()) return 0.0;
        double total = byClass.stream().mapToDouble(AttendanceByClassDTO::attendancePercentage).sum();
        return Math.round((total / byClass.size()) * 10.0) / 10.0;
    }
}
