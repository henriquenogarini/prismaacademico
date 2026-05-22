import { useEffect } from 'react';
import { NavLink } from 'react-router-dom';
import { X, Triangle, LogOut,
  LayoutDashboard, ClipboardList, Users, GraduationCap,
  BookOpen, UserCog, CalendarCheck, BarChart2, FileText, Bell, BookMarked
} from 'lucide-react';
import { useAuth } from '../../hooks/useAuth';
import type { UserRole } from '../../types/user';
import { cn } from '../../utils/cn';

interface MobileSidebarProps {
  isOpen: boolean;
  onClose: () => void;
}

const navItems = [
  { label: 'Dashboard', path: '/dashboard', icon: LayoutDashboard, roles: ['ADMIN', 'COORDINATION'] as UserRole[] },
  { label: 'Processos Seletivos', path: '/processos-seletivos', icon: ClipboardList, roles: ['ADMIN', 'COORDINATION'] as UserRole[] },
  { label: 'Inscrições', path: '/inscricoes', icon: FileText, roles: ['ADMIN', 'COORDINATION'] as UserRole[] },
  { label: 'Alunos', path: '/alunos', icon: GraduationCap, roles: ['ADMIN', 'COORDINATION'] as UserRole[] },
  { label: 'Turmas', path: '/turmas', icon: BookOpen, roles: ['ADMIN', 'COORDINATION', 'TEACHER'] as UserRole[] },
  { label: 'Professores', path: '/professores', icon: UserCog, roles: ['ADMIN', 'COORDINATION'] as UserRole[] },
  { label: 'Frequência', path: '/frequencia', icon: CalendarCheck, roles: ['ADMIN', 'COORDINATION', 'TEACHER'] as UserRole[] },
  { label: 'Área do Aluno', path: '/area-aluno', icon: BookMarked, roles: ['STUDENT'] as UserRole[] },
  { label: 'Materiais', path: '/materiais', icon: FileText, roles: ['ADMIN', 'COORDINATION', 'TEACHER', 'STUDENT'] as UserRole[] },
  { label: 'Comunicados', path: '/comunicados', icon: Bell, roles: ['ADMIN', 'COORDINATION', 'TEACHER', 'STUDENT'] as UserRole[] },
  { label: 'Relatórios', path: '/relatorios', icon: BarChart2, roles: ['ADMIN', 'COORDINATION'] as UserRole[] },
  { label: 'Usuários', path: '/usuarios', icon: Users, roles: ['ADMIN'] as UserRole[] },
];

export default function MobileSidebar({ isOpen, onClose }: MobileSidebarProps) {
  const { user, logout } = useAuth();

  useEffect(() => {
    if (isOpen) document.body.style.overflow = 'hidden';
    else document.body.style.overflow = '';
    return () => { document.body.style.overflow = ''; };
  }, [isOpen]);

  const filteredItems = navItems.filter(
    (item) => user?.role && item.roles.includes(user.role)
  );

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 md:hidden">
      <div className="absolute inset-0 bg-black/50" onClick={onClose} aria-hidden="true" />

      <aside className="absolute left-0 top-0 bottom-0 w-72 bg-prismaDark-900 text-white flex flex-col">
        <div className="flex items-center justify-between px-5 py-5 border-b border-prismaDark-800">
          <div className="flex items-center gap-3">
            <div className="w-8 h-8 rounded-lg bg-gradient-to-br from-prismaBlue-500 to-prismaCyan-600 flex items-center justify-center">
              <Triangle className="w-4 h-4 text-white fill-white" />
            </div>
            <span className="font-bold text-white">Prisma Acadêmico</span>
          </div>
          <button onClick={onClose} className="p-1.5 rounded-lg hover:bg-prismaDark-800 transition-colors" aria-label="Fechar menu">
            <X className="w-5 h-5" />
          </button>
        </div>

        <nav className="flex-1 px-3 py-4 overflow-y-auto">
          <ul className="space-y-0.5">
            {filteredItems.map((item) => {
              const Icon = item.icon;
              return (
                <li key={item.path}>
                  <NavLink
                    to={item.path}
                    onClick={onClose}
                    className={({ isActive }) =>
                      cn('sidebar-link', isActive ? 'sidebar-link-active' : 'sidebar-link-inactive')
                    }
                  >
                    <Icon className="w-5 h-5" />
                    <span>{item.label}</span>
                  </NavLink>
                </li>
              );
            })}
          </ul>
        </nav>

        <div className="px-3 py-4 border-t border-prismaDark-800">
          <button
            onClick={() => { onClose(); logout(); }}
            className="sidebar-link sidebar-link-inactive w-full text-left"
          >
            <LogOut className="w-5 h-5" />
            <span>Sair</span>
          </button>
        </div>
      </aside>
    </div>
  );
}
