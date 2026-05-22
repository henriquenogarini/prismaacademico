export type UserRole = 'ADMIN' | 'COORDINATION' | 'TEACHER' | 'STUDENT' | 'CANDIDATE';

export interface User {
  id: string;
  name: string;
  email: string;
  role: UserRole;
  avatarUrl?: string;
}
