import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Search, Eye } from 'lucide-react';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Badge from '../../components/common/Badge';
import Button from '../../components/common/Button';
import Loading from '../../components/common/Loading';
import EmptyState from '../../components/common/EmptyState';
import { candidateService } from '../../services/candidateService';
import type { Candidate } from '../../types/candidate';
import { formatDate } from '../../utils/formatDate';

export default function ApplicationsListPage() {
  const navigate = useNavigate();
  const [candidates, setCandidates] = useState<Candidate[]>([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');

  useEffect(() => {
    candidateService.list().then(setCandidates).finally(() => setLoading(false));
  }, []);

  const filtered = candidates.filter(
    (c) =>
      c.fullName.toLowerCase().includes(search.toLowerCase()) ||
      c.school.toLowerCase().includes(search.toLowerCase()) ||
      c.email.toLowerCase().includes(search.toLowerCase())
  );

  if (loading) return <Loading fullPage />;

  return (
    <div>
      <PageHeader
        title="Inscrições"
        subtitle={`${candidates.length} inscrições encontradas`}
      />

      <div className="mb-4 relative">
        <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-prismaGray-400" />
        <input
          type="text"
          placeholder="Buscar por nome, escola ou e-mail..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-full pl-9 pr-4 py-2.5 text-sm rounded-xl border border-prismaGray-200 bg-white focus:outline-none focus:ring-2 focus:ring-prismaBlue-500 focus:border-transparent"
        />
      </div>

      {filtered.length === 0 ? (
        <EmptyState title="Nenhuma inscrição encontrada" description="Tente ajustar os filtros de busca." />
      ) : (
        <Card>
          <div className="overflow-x-auto">
            <table className="w-full text-sm">
              <thead>
                <tr className="border-b border-prismaGray-100">
                  <th className="text-left px-4 py-3 text-xs font-semibold text-prismaGray-500 uppercase">Candidato</th>
                  <th className="text-left px-4 py-3 text-xs font-semibold text-prismaGray-500 uppercase hidden sm:table-cell">Escola</th>
                  <th className="text-left px-4 py-3 text-xs font-semibold text-prismaGray-500 uppercase hidden md:table-cell">Data</th>
                  <th className="text-left px-4 py-3 text-xs font-semibold text-prismaGray-500 uppercase">Status</th>
                  <th className="px-4 py-3" />
                </tr>
              </thead>
              <tbody className="divide-y divide-prismaGray-100">
                {filtered.map((c) => (
                  <tr key={c.id} className="hover:bg-prismaGray-50 transition-colors">
                    <td className="px-4 py-3">
                      <p className="font-medium text-prismaDark-800">{c.fullName}</p>
                      <p className="text-xs text-prismaGray-500">{c.email}</p>
                    </td>
                    <td className="px-4 py-3 text-prismaDark-600 hidden sm:table-cell">{c.school}</td>
                    <td className="px-4 py-3 text-prismaGray-500 text-xs hidden md:table-cell">{formatDate(c.appliedAt)}</td>
                    <td className="px-4 py-3"><Badge status={c.status} /></td>
                    <td className="px-4 py-3 text-right">
                      <Button
                        variant="ghost"
                        size="sm"
                        leftIcon={<Eye className="w-3.5 h-3.5" />}
                        onClick={() => navigate(`/inscricoes/${c.id}`)}
                      >
                        Ver
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </Card>
      )}
    </div>
  );
}
