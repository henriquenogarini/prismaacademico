import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Plus, Pencil } from 'lucide-react';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Badge from '../../components/common/Badge';
import Button from '../../components/common/Button';
import Loading from '../../components/common/Loading';
import EmptyState from '../../components/common/EmptyState';
import { selectionProcessService } from '../../services/selectionProcessService';
import type { SelectionProcess } from '../../types/selectionProcess';
import { formatDate } from '../../utils/formatDate';

export default function SelectionProcessesPage() {
  const navigate = useNavigate();
  const [processes, setProcesses] = useState<SelectionProcess[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    selectionProcessService.list().then(setProcesses).finally(() => setLoading(false));
  }, []);

  if (loading) return <Loading fullPage />;

  return (
    <div>
      <PageHeader
        title="Processos Seletivos"
        subtitle="Gerencie os processos seletivos do cursinho"
        actions={
          <Button
            variant="primary"
            size="sm"
            leftIcon={<Plus className="w-4 h-4" />}
            onClick={() => navigate('/processos-seletivos/novo')}
          >
            Novo processo
          </Button>
        }
      />

      {processes.length === 0 ? (
        <EmptyState
          title="Nenhum processo seletivo"
          description="Crie o primeiro processo seletivo para começar."
          action={
            <Button variant="primary" onClick={() => navigate('/processos-seletivos/novo')}>
              Criar processo
            </Button>
          }
        />
      ) : (
        <div className="grid gap-4">
          {processes.map((p) => (
            <Card key={p.id} hover>
              <div className="p-5 flex flex-col sm:flex-row sm:items-center gap-4">
                <div className="flex-1 min-w-0">
                  <div className="flex items-center gap-2 flex-wrap">
                    <h2 className="font-semibold text-prismaDark-800">{p.title}</h2>
                    <Badge status={p.status} />
                  </div>
                  <p className="text-sm text-prismaGray-500 mt-1">
                    {p.year}/{p.semester} · {p.vacancies} vagas ·
                    Inscrições: {formatDate(p.startDate)} até {formatDate(p.endDate)}
                  </p>
                  {p.description && (
                    <p className="text-sm text-prismaGray-600 mt-1 line-clamp-1">{p.description}</p>
                  )}
                </div>
                <Button
                  variant="outline"
                  size="sm"
                  leftIcon={<Pencil className="w-3.5 h-3.5" />}
                  onClick={() => navigate(`/processos-seletivos/${p.id}/editar`)}
                >
                  Editar
                </Button>
              </div>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
}
