import { useState } from 'react';
import { Search } from 'lucide-react';
import Card from '../../components/common/Card';
import Badge from '../../components/common/Badge';
import { mockCandidates } from '../../mocks/candidates';
import { formatDate } from '../../utils/formatDate';

export default function ApplicationStatusPage() {
  const [cpf, setCpf] = useState('');
  const [searched, setSearched] = useState(false);
  const candidate = mockCandidates.find((c) => c.cpf.replace(/\D/g, '') === cpf.replace(/\D/g, ''));

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    setSearched(true);
  };

  return (
    <div className="max-w-xl mx-auto py-16 px-4">
      <div className="text-center mb-8">
        <h1 className="text-3xl font-bold text-prismaDark-900">Status da Inscrição</h1>
        <p className="text-prismaGray-500 mt-2">Digite seu CPF para consultar o andamento da sua inscrição</p>
      </div>

      <form onSubmit={handleSearch} className="flex gap-2 mb-6">
        <input
          type="text"
          placeholder="000.000.000-00"
          value={cpf}
          onChange={(e) => setCpf(e.target.value)}
          className="flex-1 px-4 py-2.5 text-sm rounded-xl border border-prismaGray-200 focus:outline-none focus:ring-2 focus:ring-prismaBlue-500"
        />
        <button
          type="submit"
          className="btn-primary flex items-center gap-2"
        >
          <Search className="w-4 h-4" />
          Consultar
        </button>
      </form>

      {searched && !candidate && (
        <p className="text-center text-prismaGray-500">Nenhuma inscrição encontrada para este CPF.</p>
      )}

      {candidate && (
        <Card>
          <div className="p-6">
            <div className="flex items-start justify-between mb-4">
              <div>
                <h2 className="font-semibold text-prismaDark-800">{candidate.fullName}</h2>
                <p className="text-sm text-prismaGray-500">{candidate.school} · {candidate.grade}</p>
              </div>
              <Badge status={candidate.status} />
            </div>

            <p className="text-xs text-prismaGray-500 mb-4">
              Inscrito em: {formatDate(candidate.appliedAt)}
            </p>

            <h3 className="text-sm font-medium text-prismaDark-700 mb-3">Histórico</h3>
            <ol className="relative border-l border-prismaGray-200 space-y-4 ml-2">
              {candidate.statusHistory.map((h) => (
                <li key={h.id} className="ml-4">
                  <div className="absolute -left-1.5 w-3 h-3 rounded-full bg-prismaBlue-400 border-2 border-white" />
                  <p className="text-xs text-prismaGray-500">{formatDate(h.changedAt)}</p>
                  <Badge status={h.status} />
                  {h.notes && <p className="text-xs text-prismaGray-600 mt-1">{h.notes}</p>}
                </li>
              ))}
            </ol>
          </div>
        </Card>
      )}
    </div>
  );
}
