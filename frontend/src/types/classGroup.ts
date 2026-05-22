export type ClassGroupStatus = 'ACTIVE' | 'INACTIVE' | 'COMPLETED';
export type ClassGroupShift = 'MORNING' | 'AFTERNOON' | 'EVENING';

export interface ClassGroup {
  id: string;
  name: string;
  year: number;
  semester: number;
  shift: ClassGroupShift;
  studentCount: number;
  maxStudents: number;
  status: ClassGroupStatus;
  subjects: string[];
  teacherIds: string[];
  teacherNames: string[];
  averageAttendance: number;
  createdAt: string;
}
