import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeft, CheckCircle, XCircle, AlertTriangle, UserCheck } from 'lucide-react';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Badge from '../../components/common/Badge';
import Button from '../../components/common/Button';
import Loading from '../../components/common/Loading';
import { candidateService } from '../../services/candidateService';
import { useToast } from '../../hooks/useToast';
import type { Candidate, CandidateStatus } from '../../types/candidate';
import { formatDate, formatDateTime } from '../../utils/formatDate';

export default function CandidateDetailsPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { addToast } = useToast();
  const [candidate, setCandidate] = useState<Candidate | null>(null);
  const [loading, setLoading] = useState(true);
  const [updating, setUpdating] = useState(false);

  useEffect(() => {
    if (id) {
      candidateService.getById(id).then(setCandidate).finally(() => setLoading(false));
    }
  }, [id]);

  const handleUpdateStatus = async (status: CandidateStatus, notes?: string) => {
    if (!id || !candidate) return;
    setUpdating(true);
    try {
      const updated = await candidateService.updateStatus(id, status, notes);
      setCandidate(updated);
      addToast(`Status atualizado para: ${status}`, 'success');
    } catch {
      addToast('Erro ao atualizar status.', 'error');
    } finally {
      setUpdating(false);
    }
  };

  if (loading) return <Loading fullPage />;
  if (!candidate) return <div className="p-8 text-center text-prismaGray-500">Candidato não encontrado.</div>;

  return (
    <div>
      <PageHeader
        title={candidate.fullName}
        subtitle={`Inscrição em ${formatDate(candidate.appliedAt)}`}
        breadcrumbs={[
          { label: 'Inscrições', href: '/inscricoes' },
          { label: candidate.fullName },
        ]}
        actions={
          <Button variant="outline" size="sm" leftIcon={<ArrowLeft className="w-4 h-4" />} onClick={() => navigate('/inscricoes')}>
            Voltar
          </Button>
        }
      />

      <div className="grid lg:grid-cols-3 gap-4">
        <div className="lg:col-span-2 space-y-4">
          <Card>
            <div className="p-5">
              <h2 className="font-semibold text-prismaDark-800 mb-4">Dados pessoais</h2>
              <dl className="grid sm:grid-cols-2 gap-3 text-sm">
                {[
                  ['Nome completo', candidate.fullName],
                  ['CPF', candidate.cpf],
                  ['E-mail', candidate.email],
                  ['Telefone', candidate.phone],
                  ['Data de nascimento', formatDate(candidate.birthDate)],
                  ['Escola', candidate.school],
                  ['Série', candidate.grade],
                  ['Período letivo', candidate.shift],
                  ['Renda familiar (SM)', String(candidate.familyIncome)],
                  ['Endereço', candidate.address],
                  ['Cidade', candidate.city],
                  ['Estado', candidate.state],
                ].map(([label, value]) => (
                  <div key={label}>
                    <dt className="text-xs font-medium text-prismaGray-500 uppercase">{label}</dt>
                    <dd className="text-prismaDark-800">{value}</dd>
                  </div>
                ))}
              </dl>
            </div>
          </Card>

          <Card>
            <div className="p-5">
              <h2 className="font-semibold text-prismaDark-800 mb-4">Histórico de status</h2>
              <ol className="relative border-l border-prismaGray-200 space-y-4 ml-2">
                {candidate.statusHistory.map((h) => (
                  <li key={h.id} className="ml-4">
                    <div className="absolute -left-1.5 w-3 h-3 rounded-full bg-prismaBlue-400 border-2 border-white" />
                    <p className="text-xs text-prismaGray-500">{formatDateTime(h.changedAt)}</p>
                    <div className="flex items-center gap-2 mt-0.5">
                      <Badge status={h.status} />
                    </div>
                    {h.notes && <p className="text-xs text-prismaGray-600 mt-1">{h.notes}</p>}
                  </li>
                ))}
              </ol>
            </div>
          </Card>
        </div>

        <div className="space-y-4">
          <Card>
            <div className="p-5">
              <h2 className="font-semibold text-prismaDark-800 mb-3">Status atual</h2>
              <Badge status={candidate.status} />

              <div className="mt-5 space-y-2">
                <p className="text-xs font-medium text-prismaGray-500 uppercase mb-2">Ações</p>
                <Button
                  variant="primary"
                  size="sm"
                  className="w-full"
                  isLoading={updating}
                  leftIcon={<CheckCircle className="w-4 h-4" />}
                  onClick={() => handleUpdateStatus('APPROVED')}
                  disabled={candidate.status === 'APPROVED'}
                >
                  Aprovar
                </Button>
                <Button
                  variant="outline"
                  size="sm"
                  className="w-full"
                  isLoading={updating}
                  leftIcon={<AlertTriangle className="w-4 h-4" />}
                  onClick={() => handleUpdateStatus('PENDING')}
                  disabled={candidate.status === 'PENDING'}
                >
                  Marcar pendência
                </Button>
                <Button
                  variant="danger"
                  size="sm"
                  className="w-full"
                  isLoading={updating}
                  leftIcon={<XCircle className="w-4 h-4" />}
                  onClick={() => handleUpdateStatus('REJECTED')}
                  disabled={candidate.status === 'REJECTED'}
                >
                  Reprovar
                </Button>
                <Button
                  variant="outline"
                  size="sm"
                  className="w-full border-green-300 text-green-700 hover:bg-green-50"
                  isLoading={updating}
                  leftIcon={<UserCheck className="w-4 h-4" />}
                  onClick={() => handleUpdateStatus('APPROVED', 'Convertido para aluno')}
                  disabled={candidate.status !== 'APPROVED'}
                >
                  Converter em aluno
                </Button>
              </div>
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
}
