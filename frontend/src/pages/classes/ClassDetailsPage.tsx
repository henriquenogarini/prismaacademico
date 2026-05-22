import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeft } from 'lucide-react';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Badge from '../../components/common/Badge';
import Button from '../../components/common/Button';
import Loading from '../../components/common/Loading';
import { classService } from '../../services/classService';
import type { ClassGroup } from '../../types/classGroup';

export default function ClassDetailsPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [classGroup, setClassGroup] = useState<ClassGroup | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (id) classService.getById(id).then(setClassGroup).finally(() => setLoading(false));
  }, [id]);

  if (loading) return <Loading fullPage />;
  if (!classGroup) return <p className="p-8 text-center">Turma não encontrada.</p>;

  return (
    <div>
      <PageHeader
        title={classGroup.name}
        subtitle={`${classGroup.year}/${classGroup.semester} — ${classGroup.shift}`}
        breadcrumbs={[{ label: 'Turmas', href: '/turmas' }, { label: classGroup.name }]}
        actions={
          <Button variant="outline" size="sm" leftIcon={<ArrowLeft className="w-4 h-4" />} onClick={() => navigate('/turmas')}>
            Voltar
          </Button>
        }
      />
      <div className="grid lg:grid-cols-3 gap-4">
        <div className="lg:col-span-2">
          <Card>
            <div className="p-5">
              <h2 className="font-semibold text-prismaDark-800 mb-4">Detalhes da turma</h2>
              <dl className="grid sm:grid-cols-2 gap-3 text-sm">
                {[
                  ['Nome', classGroup.name],
                  ['Turno', classGroup.shift],
                  ['Ano/Semestre', `${classGroup.year}/${classGroup.semester}`],
                  ['Alunos', String(classGroup.studentCount)],  
                  ['Freq. média', `${classGroup.averageAttendance ?? 0}%`],
                ].map(([label, value]) => (
                  <div key={label}>
                    <dt className="text-xs font-medium text-prismaGray-500 uppercase">{label}</dt>
                    <dd className="text-prismaDark-800">{value}</dd>
                  </div>
                ))}
              </dl>
              {classGroup.subjects.length > 0 && (
                <div className="mt-4">
                  <p className="text-xs font-medium text-prismaGray-500 uppercase mb-2">Disciplinas</p>
                  <div className="flex flex-wrap gap-2">
                    {classGroup.subjects.map((s) => (
                      <span key={s} className="px-2.5 py-1 text-xs rounded-full bg-prismaBlue-50 text-prismaBlue-700 font-medium">{s}</span>
                    ))}
                  </div>
                </div>
              )}
            </div>
          </Card>
        </div>
        <div>
          <Card>
            <div className="p-5">
              <h2 className="font-semibold text-prismaDark-800 mb-3">Status</h2>
              <Badge status={classGroup.status} />
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
}
