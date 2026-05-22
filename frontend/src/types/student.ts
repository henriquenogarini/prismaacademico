export type StudentStatus = 'ACTIVE' | 'INACTIVE' | 'DROPOUT' | 'COMPLETED';

export interface Student {
  id: string;
  enrollmentCode: string;
  fullName: string;
  email: string;
  phone: string;
  school: string;
  classGroupId: string;
  classGroupName: string;
  status: StudentStatus;
  attendanceRate: number;
  enrolledAt: string;
  birthDate: string;
  guardianName?: string;
  guardianPhone?: string;
}
