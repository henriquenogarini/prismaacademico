export type AttendanceStatus = 'PRESENT' | 'ABSENT' | 'JUSTIFIED';

export interface AttendanceRecord {
  studentId: string;
  studentName: string;
  status: AttendanceStatus;
}

export interface Attendance {
  id: string;
  classGroupId: string;
  classGroupName: string;
  subject: string;
  date: string;
  teacherId: string;
  teacherName: string;
  records: AttendanceRecord[];
  createdAt: string;
}

export interface AttendanceFormData {
  classGroupId: string;
  subject: string;
  date: string;
  records: AttendanceRecord[];
}
