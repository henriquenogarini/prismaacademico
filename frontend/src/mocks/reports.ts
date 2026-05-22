import type {
  ReportOverview,
  StatusDistributionItem,
  AttendanceByClassItem,
  StudentsBySchoolItem,
  EnrollmentTrendItem,
} from '../types/report';

export const mockReportOverview: ReportOverview = {
  totalApplicants: 87,
  totalApproved: 60,
  totalEnrolled: 53,
  averageAttendance: 85,
  schoolsReached: 8,
  volunteerTeachers: 4,
};

export const mockStatusDistribution: StatusDistributionItem[] = [
  { name: 'Enviada', value: 8, color: '#3b82f6' },
  { name: 'Em análise', value: 12, color: '#f59e0b' },
  { name: 'Pendente', value: 5, color: '#f97316' },
  { name: 'Aprovada', value: 42, color: '#22c55e' },
  { name: 'Reprovada', value: 15, color: '#ef4444' },
  { name: 'Desistente', value: 5, color: '#94a3b8' },
];

export const mockAttendanceByClass: AttendanceByClassItem[] = [
  { classGroup: 'Turma A', attendance: 87 },
  { classGroup: 'Turma B', attendance: 82 },
  { classGroup: '2025/2 Turma A', attendance: 84 },
];

export const mockStudentsBySchool: StudentsBySchoolItem[] = [
  { school: 'C.E. Castro Alves', count: 14 },
  { school: 'C.E. Monteiro Lobato', count: 11 },
  { school: 'C.E. Cristo Rei', count: 9 },
  { school: 'E.E. Profª Maria do Carmo', count: 8 },
  { school: 'Outras escolas', count: 11 },
];

export const mockEnrollmentTrend: EnrollmentTrendItem[] = [
  { month: 'Jan', enrolled: 0, approved: 0 },
  { month: 'Fev', enrolled: 0, approved: 60 },
  { month: 'Mar', enrolled: 53, approved: 60 },
  { month: 'Abr', enrolled: 52, approved: 60 },
  { month: 'Mai', enrolled: 51, approved: 60 },
  { month: 'Jun', enrolled: 51, approved: 60 },
];
