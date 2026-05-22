import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeft } from 'lucide-react';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Badge from '../../components/common/Badge';
import Button from '../../components/common/Button';
import Loading from '../../components/common/Loading';
import { studentService } from '../../services/studentService';
import type { Student } from '../../types/student';
import { formatDate } from '../../utils/formatDate';

export default function StudentDetailsPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [student, setStudent] = useState<Student | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (id) studentService.getById(id).then(setStudent).finally(() => setLoading(false));
  }, [id]);

  if (loading) return <Loading fullPage />;
  if (!student) return <p className="p-8 text-center">Aluno não encontrado.</p>;

  return (
    <div>
      <PageHeader
        title={student.fullName}
        breadcrumbs={[{ label: 'Alunos', href: '/alunos' }, { label: student.fullName }]}
        actions={
          <Button variant="outline" size="sm" leftIcon={<ArrowLeft className="w-4 h-4" />} onClick={() => navigate('/alunos')}>
            Voltar
          </Button>
        }
      />
      <div className="grid lg:grid-cols-3 gap-4">
        <div className="lg:col-span-2">
          <Card>
            <div className="p-5">
              <h2 className="font-semibold text-prismaDark-800 mb-4">Informações do aluno</h2>
              <dl className="grid sm:grid-cols-2 gap-3 text-sm">
                {[
                  ['Nome', student.fullName],
                  ['E-mail', student.email],
                  ['Matrícula', student.enrollmentCode ?? '—'],
                  ['Turma', student.classGroupId ?? '—'],
                  ['Frequência', `${student.attendanceRate ?? 0}%`],
                  ['Data de matrícula', student.enrolledAt ? formatDate(student.enrolledAt) : '—'],
                  ['Escola', student.school ?? '—'],
                  ['Telefone', student.phone ?? '—'],
                ].map(([label, value]) => (
                  <div key={label}>
                    <dt className="text-xs font-medium text-prismaGray-500 uppercase">{label}</dt>
                    <dd className="text-prismaDark-800">{value}</dd>
                  </div>
                ))}
              </dl>
            </div>
          </Card>
        </div>
        <div>
          <Card>
            <div className="p-5">
              <h2 className="font-semibold text-prismaDark-800 mb-3">Status</h2>
              <Badge status={student.status} />
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
}
