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
};

export function hasPermission(role: UserRole, path: string): boolean {
  const matchingRoute = Object.keys(routeRoles).find(
    (route) => path === route || path.startsWith(route + '/')
  );
  if (!matchingRoute) return true;
  return routeRoles[matchingRoute].includes(role);
}

export function getDefaultRoute(role: UserRole): string {
  switch (role) {
    case 'ADMIN':
    case 'COORDINATION':
      return '/dashboard';
    case 'TEACHER':
      return '/turmas';
    case 'STUDENT':
      return '/area-aluno';
    case 'CANDIDATE':
      return '/status-inscricao';
    default:
      return '/login';
  }
}

export function canAccess(role: UserRole, ...roles: UserRole[]): boolean {
  return roles.includes(role);
}
