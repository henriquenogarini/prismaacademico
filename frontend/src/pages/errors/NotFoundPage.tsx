import { Link } from 'react-router-dom';
import { Home, ArrowLeft } from 'lucide-react';

export default function NotFoundPage() {
  return (
    <div className="min-h-screen bg-prismaLight flex items-center justify-center px-4">
      <div className="text-center max-w-md">
        <p className="text-8xl font-extrabold text-prismaBlue-700 mb-2">404</p>
        <h1 className="text-2xl font-bold text-prismaDark-900 mb-2">Página não encontrada</h1>
        <p className="text-prismaGray-500 mb-8">
          A página que você procura não existe ou foi movida.
        </p>
        <div className="flex gap-3 justify-center">
          <Link
            to="/"
            className="inline-flex items-center gap-2 px-5 py-2.5 rounded-xl bg-prismaBlue-700 text-white font-medium hover:bg-prismaBlue-800 transition-colors"
          >
            <Home className="w-4 h-4" />
            Início
          </Link>
          <button
            onClick={() => history.back()}
            className="inline-flex items-center gap-2 px-5 py-2.5 rounded-xl border border-prismaGray-200 text-prismaDark-700 font-medium hover:bg-prismaGray-50 transition-colors"
          >
            <ArrowLeft className="w-4 h-4" />
            Voltar
          </button>
        </div>
      </div>
    </div>
  );
}
