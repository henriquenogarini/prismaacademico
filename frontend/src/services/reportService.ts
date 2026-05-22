import type {
  ReportOverview,
  StatusDistributionItem,
  AttendanceByClassItem,
  StudentsBySchoolItem,
  EnrollmentTrendItem,
} from '../types/report';
import {
  mockReportOverview,
  mockStatusDistribution,
  mockAttendanceByClass,
  mockStudentsBySchool,
  mockEnrollmentTrend,
} from '../mocks/reports';
import api from '../api/axios';
import { ENDPOINTS } from '../api/endpoints';

const useMocks = () => import.meta.env.VITE_USE_MOCKS === 'true';

export const reportService = {
  async getOverview(): Promise<ReportOverview> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 400));
      return { ...mockReportOverview };
    }
    const res = await api.get<ReportOverview>(ENDPOINTS.reports.overview);
    return res.data;
  },

  async getStatusDistribution(): Promise<StatusDistributionItem[]> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 300));
      return [...mockStatusDistribution];
    }
    const res = await api.get<StatusDistributionItem[]>(ENDPOINTS.reports.statusDistribution);
    return res.data;
  },

  async getAttendanceByClass(): Promise<AttendanceByClassItem[]> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 300));
      return [...mockAttendanceByClass];
    }
    const res = await api.get<AttendanceByClassItem[]>(ENDPOINTS.reports.attendanceByClass);
    return res.data;
  },

  async getStudentsBySchool(): Promise<StudentsBySchoolItem[]> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 300));
      return [...mockStudentsBySchool];
    }
    const res = await api.get<StudentsBySchoolItem[]>(ENDPOINTS.reports.studentsBySchool);
    return res.data;
  },

  async getEnrollmentTrend(): Promise<EnrollmentTrendItem[]> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 300));
      return [...mockEnrollmentTrend];
    }
    const res = await api.get<EnrollmentTrendItem[]>(ENDPOINTS.reports.enrollmentTrend);
    return res.data;
  },
};
