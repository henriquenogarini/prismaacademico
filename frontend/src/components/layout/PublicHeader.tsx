import { Link } from 'react-router-dom';
import { Triangle } from 'lucide-react';

export default function PublicHeader() {
  return (
    <header className="bg-white/95 backdrop-blur-sm border-b border-prismaGray-200 sticky top-0 z-40">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 h-16 flex items-center justify-between">
        <Link to="/" className="flex items-center gap-3 group" aria-label="Prisma Acadêmico — Início">
          <div className="w-9 h-9 rounded-xl bg-gradient-to-br from-prismaBlue-700 to-prismaCyan-600 flex items-center justify-center shadow-prisma">
            <Triangle className="w-5 h-5 text-white fill-white" />
          </div>
          <div>
            <span className="font-bold text-prismaDark-900 group-hover:text-prismaBlue-700 transition-colors">
              Prisma Acadêmico
            </span>
            <p className="text-xs text-prismaGray-500 leading-none">UTFPR-CP</p>
          </div>
        </Link>

        <div className="flex items-center gap-3">
          <Link
            to="/inscricao"
            className="hidden sm:inline-flex items-center px-4 py-2 text-sm font-medium text-prismaBlue-700 border border-prismaBlue-300 rounded-xl hover:bg-prismaBlue-50 transition-colors"
          >
            Realizar inscrição
          </Link>
          <Link
            to="/login"
            className="inline-flex items-center px-4 py-2 text-sm font-medium text-white bg-prismaBlue-700 rounded-xl hover:bg-prismaBlue-800 transition-colors shadow-prisma"
          >
            Acessar sistema
          </Link>
        </div>
      </div>
    </header>
  );
}
