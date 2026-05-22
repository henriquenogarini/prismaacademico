import { Menu, Bell, Triangle } from 'lucide-react';
import { useAuth } from '../../hooks/useAuth';

interface TopbarProps {
  onMenuClick: () => void;
}

const roleLabels: Record<string, string> = {
  ADMIN: 'Administrador',
  COORDINATION: 'Coordenação',
  TEACHER: 'Professor(a)',
  STUDENT: 'Aluno(a)',
  CANDIDATE: 'Candidato(a)',
};

export default function Topbar({ onMenuClick }: TopbarProps) {
  const { user, logout } = useAuth();

  return (
    <header className="h-16 bg-white border-b border-prismaGray-200 flex items-center px-4 gap-4 flex-shrink-0">
      <button
        onClick={onMenuClick}
        className="md:hidden p-2 rounded-xl text-prismaGray-500 hover:bg-prismaGray-100 transition-colors"
        aria-label="Abrir menu"
      >
        <Menu className="w-5 h-5" />
      </button>

      <div className="md:hidden flex items-center gap-2">
        <div className="w-7 h-7 rounded-lg bg-gradient-to-br from-prismaBlue-500 to-prismaCyan-600 flex items-center justify-center">
          <Triangle className="w-4 h-4 text-white fill-white" />
        </div>
        <span className="text-sm font-bold text-prismaDark-900">Prisma</span>
      </div>

      <div className="flex-1" />

      <button
        className="p-2 rounded-xl text-prismaGray-500 hover:bg-prismaGray-100 transition-colors relative"
        aria-label="Notificações"
      >
        <Bell className="w-5 h-5" />
        <span className="absolute top-1.5 right-1.5 w-2 h-2 rounded-full bg-red-500" aria-hidden="true" />
      </button>

      <div className="flex items-center gap-3">
        <div className="hidden sm:block text-right">
          <p className="text-sm font-semibold text-prismaDark-800 leading-tight">
            {user?.name}
          </p>
          <p className="text-xs text-prismaGray-500 leading-tight">
            {user?.role ? roleLabels[user.role] : ''}
          </p>
        </div>
        <div className="w-9 h-9 rounded-xl bg-gradient-to-br from-prismaIndigo-500 to-prismaCyan-600 flex items-center justify-center cursor-pointer"
          onClick={logout}
          title="Clique para sair"
          role="button"
          tabIndex={0}
          aria-label="Sair da conta"
          onKeyDown={(e) => e.key === 'Enter' && logout()}
        >
          <span className="text-sm font-bold text-white">
            {user?.name.charAt(0).toUpperCase()}
          </span>
        </div>
      </div>
    </header>
  );
}
