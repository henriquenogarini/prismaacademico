package br.edu.utfpr.prismaacademico.reports.dto;

public record ExtensionSummaryDTO(
        long totalApplicants,
        long totalEnrolled,
        long volunteerTeachers,
        long schoolsServed,
        double averageAttendance,
        long activeClasses
) {}
