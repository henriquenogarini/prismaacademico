package br.edu.utfpr.prismaacademico.reports;

import br.edu.utfpr.prismaacademico.attendance.repository.AttendanceRepository;
import br.edu.utfpr.prismaacademico.candidates.enums.CandidateStatus;
import br.edu.utfpr.prismaacademico.candidates.repository.CandidateRepository;
import br.edu.utfpr.prismaacademico.classgroups.enums.ClassStatus;
import br.edu.utfpr.prismaacademico.classgroups.repository.ClassGroupRepository;
import br.edu.utfpr.prismaacademico.reports.dto.*;
import br.edu.utfpr.prismaacademico.reports.service.ReportService;
import br.edu.utfpr.prismaacademico.students.enums.StudentStatus;
import br.edu.utfpr.prismaacademico.students.repository.StudentRepository;
import br.edu.utfpr.prismaacademico.teachers.repository.TeacherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReportService - Testes Unitários")
class ReportServiceTest {

    @Mock private CandidateRepository candidateRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private ClassGroupRepository classGroupRepository;
    @Mock private TeacherRepository teacherRepository;
    @Mock private AttendanceRepository attendanceRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    @DisplayName("getOverview deve retornar dados agregados corretamente")
    void getOverview_returnsAggregatedData() {
        when(candidateRepository.count()).thenReturn(100L);
        when(candidateRepository.countByStatus(CandidateStatus.APPROVED)).thenReturn(60L);
        when(candidateRepository.countByStatus(CandidateStatus.REJECTED)).thenReturn(20L);
        when(candidateRepository.countByStatus(CandidateStatus.PENDING)).thenReturn(5L);
        when(candidateRepository.countByStatus(CandidateStatus.SUBMITTED)).thenReturn(10L);
        when(candidateRepository.countByStatus(CandidateStatus.UNDER_REVIEW)).thenReturn(5L);
        when(studentRepository.count()).thenReturn(55L);
        when(studentRepository.countByStatus(StudentStatus.ACTIVE)).thenReturn(50L);
        when(classGroupRepository.countByStatus(ClassStatus.ACTIVE)).thenReturn(4L);
        when(teacherRepository.countByActive(true)).thenReturn(8L);
        when(attendanceRepository.attendancePercentageByClass()).thenReturn(List.of());

        ReportOverviewDTO result = reportService.getOverview();

        assertThat(result).isNotNull();
        assertThat(result.totalCandidates()).isEqualTo(100L);
        assertThat(result.approvedCandidates()).isEqualTo(60L);
        assertThat(result.rejectedCandidates()).isEqualTo(20L);
        assertThat(result.pendingCandidates()).isEqualTo(20L);
        assertThat(result.enrolledStudents()).isEqualTo(55L);
        assertThat(result.activeStudents()).isEqualTo(50L);
        assertThat(result.activeClasses()).isEqualTo(4L);
        assertThat(result.volunteerTeachers()).isEqualTo(8L);
    }

    @Test
    @DisplayName("getApplicationsByStatus deve retornar lista com labels traduzidos")
    void getApplicationsByStatus_returnsLabelledList() {
        when(candidateRepository.countGroupedByStatus()).thenReturn(List.of(
                new Object[]{"APPROVED", 60L},
                new Object[]{"REJECTED", 20L}
        ));

        List<ApplicationsByStatusDTO> result = reportService.getApplicationsByStatus();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).status()).isEqualTo("APPROVED");
        assertThat(result.get(0).label()).isEqualTo("Aprovada");
        assertThat(result.get(0).count()).isEqualTo(60L);
    }

    @Test
    @DisplayName("getExtensionSummary deve retornar resumo do projeto de extensão")
    void getExtensionSummary_returnsSummary() {
        when(candidateRepository.count()).thenReturn(200L);
        when(studentRepository.count()).thenReturn(120L);
        when(teacherRepository.countByActive(true)).thenReturn(15L);
        when(classGroupRepository.countByStatus(ClassStatus.ACTIVE)).thenReturn(6L);
        when(candidateRepository.countGroupedBySchool()).thenReturn(List.of(
                new Object[]{"Escola A", 50L},
                new Object[]{"Escola B", 70L}
        ));
        when(attendanceRepository.attendancePercentageByClass()).thenReturn(List.of());

        ExtensionSummaryDTO result = reportService.getExtensionSummary();

        assertThat(result).isNotNull();
        assertThat(result.totalApplicants()).isEqualTo(200L);
        assertThat(result.totalEnrolled()).isEqualTo(120L);
        assertThat(result.volunteerTeachers()).isEqualTo(15L);
        assertThat(result.schoolsServed()).isEqualTo(2L);
        assertThat(result.activeClasses()).isEqualTo(6L);
    }
}
