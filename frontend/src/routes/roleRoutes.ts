import type { UserRole } from '../types/user';

export const routeRoles: Record<string, UserRole[]> = {
  '/dashboard': ['ADMIN', 'COORDINATION'],
  '/processos-seletivos': ['ADMIN', 'COORDINATION'],
  '/inscricoes': ['ADMIN', 'COORDINATION'],
  '/alunos': ['ADMIN', 'COORDINATION'],
  '/turmas': ['ADMIN', 'COORDINATION', 'TEACHER'],
  '/professores': ['ADMIN', 'COORDINATION'],
  '/frequencia': ['ADMIN', 'COORDINATION', 'TEACHER'],
  '/area-aluno': ['STUDENT'],
  '/materiais': ['ADMIN', 'COORDINATION', 'TEACHER', 'STUDENT'],
  '/comunicados': ['ADMIN', 'COORDINATION', 'TEACHER', 'STUDENT'],
  '/relatorios': ['ADMIN', 'COORDINATION'],
  '/usuarios': ['ADMIN'],
};
