import { useEffect, useState } from 'react';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Badge from '../../components/common/Badge';
import Loading from '../../components/common/Loading';
import { teacherService } from '../../services/teacherService';
import type { Teacher } from '../../types/teacher';

export default function TeachersPage() {
  const [teachers, setTeachers] = useState<Teacher[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    teacherService.list().then(setTeachers).finally(() => setLoading(false));
  }, []);

  if (loading) return <Loading fullPage />;

  return (
    <div>
      <PageHeader title="Professores" subtitle={`${teachers.length} professores cadastrados`} />
      <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {teachers.map((t) => (
          <Card key={t.id} hover>
            <div className="p-5">
              <div className="flex items-start justify-between mb-2">
                <div>
                  <h2 className="font-semibold text-prismaDark-800">{t.fullName}</h2>
                  <p className="text-sm text-prismaGray-500">{t.course}</p>
                </div>
                <Badge status={t.status} />
              </div>
              <p className="text-xs text-prismaGray-500 mb-3">{t.institution}</p>
              <div className="flex flex-wrap gap-1.5 mb-3">
                {t.subjects.map((s) => (
                  <span key={s} className="px-2 py-0.5 text-xs rounded-full bg-prismaBlue-50 text-prismaBlue-700">{s}</span>
                ))}
              </div>
              <div className="flex gap-4 text-sm text-prismaGray-600">
                <span>Aulas: <strong className="text-prismaDark-700">{t.lessonsCount}</strong></span>
                <span>Horas: <strong className="text-prismaDark-700">{t.estimatedHours}h</strong></span>
              </div>
            </div>
          </Card>
        ))}
      </div>
    </div>
  );
}
