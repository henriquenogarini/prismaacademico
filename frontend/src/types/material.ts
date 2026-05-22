export type MaterialType = 'PDF' | 'VIDEO' | 'SLIDE' | 'EXERCISE' | 'OTHER';

export interface Material {
  id: string;
  title: string;
  subject: string;
  teacherId: string;
  teacherName: string;
  type: MaterialType;
  fileUrl?: string;
  description?: string;
  classGroupId?: string;
  classGroupName?: string;
  createdAt: string;
}
