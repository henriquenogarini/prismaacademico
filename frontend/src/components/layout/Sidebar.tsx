import { NavLink } from 'react-router-dom';
import {
  LayoutDashboard,
  ClipboardList,
  Users,
  GraduationCap,
  BookOpen,
  UserCog,
  CalendarCheck,
  BarChart2,
  FileText,
  Bell,
  BookMarked,
  LogOut,
  Triangle,
} from 'lucide-react';
import { useAuth } from '../../hooks/useAuth';
import type { UserRole } from '../../types/user';
import { cn } from '../../utils/cn';

interface NavItem {
  label: string;
  path: string;
  icon: React.ComponentType<{ className?: string }>;
  roles: UserRole[];
}

const navItems: NavItem[] = [
  {
    label: 'Dashboard',
    path: '/dashboard',
    icon: LayoutDashboard,
    roles: ['ADMIN', 'COORDINATION'],
  },
  {
    label: 'Processos Seletivos',
    path: '/processos-seletivos',
    icon: ClipboardList,
    roles: ['ADMIN', 'COORDINATION'],
  },
  {
    label: 'Inscrições',
    path: '/inscricoes',
    icon: FileText,
    roles: ['ADMIN', 'COORDINATION'],
  },
  {
    label: 'Alunos',
    path: '/alunos',
    icon: GraduationCap,
    roles: ['ADMIN', 'COORDINATION'],
  },
  {
    label: 'Turmas',
    path: '/turmas',
    icon: BookOpen,
    roles: ['ADMIN', 'COORDINATION', 'TEACHER'],
  },
  {
    label: 'Professores',
    path: '/professores',
    icon: UserCog,
    roles: ['ADMIN', 'COORDINATION'],
  },
  {
    label: 'Frequência',
    path: '/frequencia',
    icon: CalendarCheck,
    roles: ['ADMIN', 'COORDINATION', 'TEACHER'],
  },
  {
    label: 'Área do Aluno',
    path: '/area-aluno',
    icon: BookMarked,
    roles: ['STUDENT'],
  },
  {
    label: 'Materiais',
    path: '/materiais',
    icon: FileText,
    roles: ['ADMIN', 'COORDINATION', 'TEACHER', 'STUDENT'],
  },
  {
    label: 'Comunicados',
    path: '/comunicados',
    icon: Bell,
    roles: ['ADMIN', 'COORDINATION', 'TEACHER', 'STUDENT'],
  },
  {
    label: 'Relatórios',
    path: '/relatorios',
    icon: BarChart2,
    roles: ['ADMIN', 'COORDINATION'],
  },
  {
    label: 'Usuários',
    path: '/usuarios',
    icon: Users,
    roles: ['ADMIN'],
  },
];

export default function Sidebar() {
  const { user, logout } = useAuth();

  const filteredItems = navItems.filter(
    (item) => user?.role && item.roles.includes(user.role)
  );

  return (
    <aside className="hidden md:flex flex-col w-64 h-screen bg-prismaDark-900 text-white flex-shrink-0">
      <div className="flex items-center gap-3 px-5 py-5 border-b border-prismaDark-800">
        <div className="w-9 h-9 rounded-xl bg-gradient-to-br from-prismaBlue-500 to-prismaCyan-600 flex items-center justify-center flex-shrink-0">
          <Triangle className="w-5 h-5 text-white fill-white" />
        </div>
        <div>
          <p className="text-sm font-bold leading-tight text-white">Prisma Acadêmico</p>
          <p className="text-xs text-prismaDark-400 leading-tight">UTFPR-CP</p>
        </div>
      </div>

      <nav className="flex-1 px-3 py-4 overflow-y-auto" aria-label="Menu principal">
        <ul className="space-y-0.5">
          {filteredItems.map((item) => {
            const Icon = item.icon;
            return (
              <li key={item.path}>
                <NavLink
                  to={item.path}
                  className={({ isActive }) =>
                    cn(
                      'sidebar-link',
                      isActive ? 'sidebar-link-active' : 'sidebar-link-inactive'
                    )
                  }
                >
                  <Icon className="w-4.5 h-4.5 flex-shrink-0 w-5 h-5" />
                  <span>{item.label}</span>
                </NavLink>
              </li>
            );
          })}
        </ul>
      </nav>

      <div className="px-3 py-4 border-t border-prismaDark-800">
        <div className="flex items-center gap-3 px-3 py-2 mb-1">
          <div className="w-8 h-8 rounded-full bg-gradient-to-br from-prismaIndigo-500 to-prismaCyan-600 flex items-center justify-center flex-shrink-0">
            <span className="text-xs font-bold text-white">
              {user?.name.charAt(0).toUpperCase()}
            </span>
          </div>
          <div className="min-w-0">
            <p className="text-sm font-medium text-white truncate">{user?.name}</p>
            <p className="text-xs text-prismaDark-400 truncate">{user?.email}</p>
          </div>
        </div>
        <button
          onClick={logout}
          className="sidebar-link sidebar-link-inactive w-full text-left"
          aria-label="Sair da conta"
        >
          <LogOut className="w-5 h-5" />
          <span>Sair</span>
        </button>
      </div>
    </aside>
  );
}
