export interface Lesson {
  id: string;
  classGroupId: string;
  classGroupName: string;
  subject: string;
  teacherId: string;
  teacherName: string;
  date: string;
  startTime: string;
  endTime: string;
  topic?: string;
  notes?: string;
}
