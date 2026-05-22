package br.edu.utfpr.prismaacademico.reports.dto;

public record ReportOverviewDTO(
        long totalCandidates,
        long approvedCandidates,
        long rejectedCandidates,
        long pendingCandidates,
        long enrolledStudents,
        long activeStudents,
        long activeClasses,
        long volunteerTeachers,
        double averageAttendance
) {}
