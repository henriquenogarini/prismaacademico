export interface ReportOverview {
  totalApplicants: number;
  totalApproved: number;
  totalEnrolled: number;
  averageAttendance: number;
  schoolsReached: number;
  volunteerTeachers: number;
}

export interface StatusDistributionItem {
  name: string;
  value: number;
  color: string;
}

export interface AttendanceByClassItem {
  classGroup: string;
  attendance: number;
}

export interface StudentsBySchoolItem {
  school: string;
  count: number;
}

export interface EnrollmentTrendItem {
  month: string;
  enrolled: number;
  approved: number;
}
