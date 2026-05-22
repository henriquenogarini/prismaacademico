import { useEffect, useState } from 'react';
import { Download } from 'lucide-react';
import {
  BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer,
  PieChart, Pie, Cell, Legend
} from 'recharts';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Button from '../../components/common/Button';
import Loading from '../../components/common/Loading';
import { reportService } from '../../services/reportService';
import type { ReportOverview, StatusDistributionItem, AttendanceByClassItem, StudentsBySchoolItem } from '../../types/report';
import { useToast } from '../../hooks/useToast';

const COLORS = ['#1d4ed8', '#0891b2', '#4338ca', '#7c3aed', '#d97706', '#16a34a'];

export default function ReportsPage() {
  const { addToast } = useToast();
  const [overview, setOverview] = useState<ReportOverview | null>(null);
  const [statusDist, setStatusDist] = useState<StatusDistributionItem[]>([]);
  const [attendanceByClass, setAttendanceByClass] = useState<AttendanceByClassItem[]>([]);
  const [bySchool, setBySchool] = useState<StudentsBySchoolItem[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      reportService.getOverview(),
      reportService.getStatusDistribution(),
      reportService.getAttendanceByClass(),
      reportService.getStudentsBySchool(),
    ]).then(([ov, dist, att, school]) => {
      setOverview(ov);
      setStatusDist(dist);
      setAttendanceByClass(att);
      setBySchool(school);
    }).finally(() => setLoading(false));
  }, []);

  if (loading) return <Loading fullPage />;

  const handleExport = () => {
    addToast('Relatório exportado! (simulado)', 'success');
  };

  return (
    <div>
      <PageHeader
        title="Relatórios"
        subtitle="Indicadores gerais do Cursinho Prisma"
        actions={
          <Button variant="outline" size="sm" leftIcon={<Download className="w-4 h-4" />} onClick={handleExport}>
            Exportar
          </Button>
        }
      />

      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-6">
        {[
          { label: 'Total de inscritos', value: overview?.totalApplicants },
          { label: 'Aprovados', value: overview?.totalApproved },
          { label: 'Alunos ativos', value: overview?.totalEnrolled },
          { label: 'Freq. média', value: `${overview?.averageAttendance ?? 0}%` },
        ].map(({ label, value }) => (
          <Card key={label}>
            <div className="p-4 text-center">
              <p className="text-2xl font-bold text-prismaBlue-700">{value}</p>
              <p className="text-xs text-prismaGray-500 mt-1">{label}</p>
            </div>
          </Card>
        ))}
      </div>

      <div className="grid lg:grid-cols-2 gap-4 mb-4">
        <Card>
          <div className="p-5">
            <h2 className="font-semibold text-prismaDark-800 mb-4">Distribuição por status</h2>
            <ResponsiveContainer width="100%" height={220}>
              <PieChart>
                <Pie data={statusDist.map((d) => ({ name: d.name, value: d.value }))} dataKey="value" cx="50%" cy="50%" outerRadius={80} label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`} labelLine={false}>
                  {statusDist.map((_, i) => (
                    <Cell key={i} fill={COLORS[i % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip />
              </PieChart>
            </ResponsiveContainer>
          </div>
        </Card>

        <Card>
          <div className="p-5">
            <h2 className="font-semibold text-prismaDark-800 mb-4">Frequência por turma</h2>
            <ResponsiveContainer width="100%" height={220}>
              <BarChart data={attendanceByClass} margin={{ top: 5, right: 10, left: -10, bottom: 5 }}>
                <CartesianGrid strokeDasharray="3 3" stroke="#e2e8f0" />
                <XAxis dataKey="className" tick={{ fontSize: 11 }} axisLine={false} tickLine={false} />
                <YAxis tick={{ fontSize: 11 }} axisLine={false} tickLine={false} domain={[0, 100]} unit="%" />
                <Tooltip formatter={(v: number) => [`${v}%`, 'Frequência']} />
                <Bar dataKey="averageAttendance" radius={[6, 6, 0, 0]} fill="#0891b2" />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </Card>
      </div>

      <Card>
        <div className="p-5">
          <h2 className="font-semibold text-prismaDark-800 mb-4">Alunos por escola</h2>
          <ResponsiveContainer width="100%" height={200}>
            <BarChart data={bySchool} layout="vertical" margin={{ top: 0, right: 20, left: 100, bottom: 0 }}>
              <CartesianGrid strokeDasharray="3 3" stroke="#e2e8f0" horizontal={false} />
              <XAxis type="number" tick={{ fontSize: 11 }} axisLine={false} tickLine={false} />
              <YAxis dataKey="school" type="category" tick={{ fontSize: 11 }} axisLine={false} tickLine={false} width={100} />
              <Tooltip />
              <Bar dataKey="count" radius={[0, 6, 6, 0]} fill="#4338ca" />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </Card>
    </div>
  );
}
