import { Link, useNavigate } from 'react-router-dom';
import { ShieldOff, ArrowLeft } from 'lucide-react';

export default function AccessDeniedPage() {
  const navigate = useNavigate();

  return (
    <div className="flex items-center justify-center min-h-[60vh] px-4">
      <div className="text-center max-w-md">
        <div className="w-16 h-16 rounded-2xl bg-red-50 flex items-center justify-center mx-auto mb-4">
          <ShieldOff className="w-8 h-8 text-red-500" />
        </div>
        <h1 className="text-2xl font-bold text-prismaDark-900 mb-2">Acesso negado</h1>
        <p className="text-prismaGray-500 mb-8">
          Você não tem permissão para acessar esta página.
          Entre em contato com a coordenação se acredita que isso é um erro.
        </p>
        <div className="flex gap-3 justify-center">
          <button
            onClick={() => navigate(-1)}
            className="inline-flex items-center gap-2 px-5 py-2.5 rounded-xl border border-prismaGray-200 text-prismaDark-700 font-medium hover:bg-prismaGray-50 transition-colors"
          >
            <ArrowLeft className="w-4 h-4" />
            Voltar
          </button>
          <Link
            to="/login"
            className="inline-flex items-center gap-2 px-5 py-2.5 rounded-xl bg-prismaBlue-700 text-white font-medium hover:bg-prismaBlue-800 transition-colors"
          >
            Ir ao login
          </Link>
        </div>
      </div>
    </div>
  );
}
