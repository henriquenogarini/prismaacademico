import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Users, ClipboardList, GraduationCap, BookOpen,
  UserCog, TrendingUp, Plus, Eye
} from 'lucide-react';
import PageHeader from '../../components/common/PageHeader';
import MetricCard from '../../components/dashboard/MetricCard';
import RecentApplicationsTable from '../../components/dashboard/RecentApplicationsTable';
import { DashboardBarChart, DashboardAreaChart } from '../../components/dashboard/DashboardChart';
import Card from '../../components/common/Card';
import Button from '../../components/common/Button';
import Loading from '../../components/common/Loading';
import { reportService } from '../../services/reportService';
import { candidateService } from '../../services/candidateService';
import type { ReportOverview, StatusDistributionItem, EnrollmentTrendItem } from '../../types/report';
import type { Candidate } from '../../types/candidate';

export default function CoordinationDashboardPage() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [overview, setOverview] = useState<ReportOverview | null>(null);
  const [statusDist, setStatusDist] = useState<StatusDistributionItem[]>([]);
  const [trend, setTrend] = useState<EnrollmentTrendItem[]>([]);
  const [candidates, setCandidates] = useState<Candidate[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const [ov, dist, tr, cands] = await Promise.all([
          reportService.getOverview(),
          reportService.getStatusDistribution(),
          reportService.getEnrollmentTrend(),
          candidateService.list(),
        ]);
        setOverview(ov);
        setStatusDist(dist);
        setTrend(tr);
        setCandidates(cands);
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  if (loading) return <Loading fullPage message="Carregando dashboard..." />;

  const barData = statusDist.map((d) => ({ name: d.name, value: d.value }));
  const areaData = trend.map((d) => ({ month: d.month, enrolled: d.enrolled, approved: d.approved }));

  return (
    <div>
      <PageHeader
        title="Dashboard"
        subtitle="Visão geral do Cursinho Prisma"
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

      <div className="grid grid-cols-2 lg:grid-cols-3 xl:grid-cols-6 gap-4 mb-6">
        <MetricCard
          title="Total de inscritos"
          value={overview?.totalApplicants ?? 0}
          icon={<ClipboardList className="w-5 h-5" />}
          color="blue"
        />
        <MetricCard
          title="Aprovados"
          value={overview?.totalApproved ?? 0}
          icon={<TrendingUp className="w-5 h-5" />}
          color="green"
        />
        <MetricCard
          title="Alunos ativos"
          value={overview?.totalEnrolled ?? 0}
          icon={<GraduationCap className="w-5 h-5" />}
          color="cyan"
        />
        <MetricCard
          title="Turmas ativas"
          value={overview?.schoolsReached ?? 0}
          icon={<BookOpen className="w-5 h-5" />}
          color="indigo"
        />
        <MetricCard
          title="Professores"
          value={overview?.volunteerTeachers ?? 0}
          icon={<UserCog className="w-5 h-5" />}
          color="purple"
        />
        <MetricCard
          title="Freq. média"
          value={`${overview?.averageAttendance ?? 0}%`}
          icon={<Users className="w-5 h-5" />}
          color="amber"
        />
      </div>

      <div className="grid lg:grid-cols-2 gap-4 mb-6">
        <Card>
          <div className="p-5">
            <h2 className="font-semibold text-prismaDark-800 mb-4">Distribuição por status</h2>
            <DashboardBarChart data={barData} />
          </div>
        </Card>
        <Card>
          <div className="p-5">
            <h2 className="font-semibold text-prismaDark-800 mb-4">Evolução de matrículas</h2>
            <DashboardAreaChart data={areaData} />
          </div>
        </Card>
      </div>

      <Card>
        <div className="p-5">
          <div className="flex items-center justify-between mb-4">
            <h2 className="font-semibold text-prismaDark-800">Inscrições recentes</h2>
            <Button
              variant="ghost"
              size="sm"
              rightIcon={<Eye className="w-4 h-4" />}
              onClick={() => navigate('/inscricoes')}
            >
              Ver todas
            </Button>
          </div>
          <RecentApplicationsTable candidates={candidates} />
        </div>
      </Card>
    </div>
  );
}
