export type TeacherStatus = 'ACTIVE' | 'INACTIVE';

export interface Teacher {
  id: string;
  fullName: string;
  email: string;
  phone: string;
  course: string;
  institution: string;
  subjects: string[];
  status: TeacherStatus;
  lessonsCount: number;
  estimatedHours: number;
  joinedAt: string;
  bio?: string;
}
