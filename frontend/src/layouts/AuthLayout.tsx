import { Outlet } from 'react-router-dom';
import { Triangle } from 'lucide-react';

export default function AuthLayout() {
  return (
    <div className="min-h-screen bg-gradient-hero flex flex-col">
      <div className="absolute inset-0 overflow-hidden pointer-events-none" aria-hidden="true">
        <div className="absolute -top-40 -right-40 w-96 h-96 rounded-full bg-prismaBlue-600/20 blur-3xl" />
        <div className="absolute -bottom-40 -left-40 w-96 h-96 rounded-full bg-prismaCyan-600/20 blur-3xl" />
      </div>

      <header className="relative z-10 px-6 py-5 flex items-center gap-3">
        <div className="w-9 h-9 rounded-xl bg-white/20 backdrop-blur-sm flex items-center justify-center border border-white/30">
          <Triangle className="w-5 h-5 text-white fill-white" />
        </div>
        <div>
          <p className="text-sm font-bold text-white">Prisma Acadêmico</p>
          <p className="text-xs text-white/60">UTFPR-CP</p>
        </div>
      </header>

      <main className="relative z-10 flex-1 flex items-center justify-center px-4 py-8">
        <Outlet />
      </main>

      <footer className="relative z-10 py-4 text-center">
        <p className="text-xs text-white/50">
          Prisma Acadêmico — Ferramenta auxiliar de gestão extensionista · UTFPR-CP · MIT License
        </p>
      </footer>
    </div>
  );
}
