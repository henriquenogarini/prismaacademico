import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Eye } from 'lucide-react';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Badge from '../../components/common/Badge';
import Button from '../../components/common/Button';
import Loading from '../../components/common/Loading';
import { classService } from '../../services/classService';
import type { ClassGroup } from '../../types/classGroup';

export default function ClassesPage() {
  const navigate = useNavigate();
  const [classes, setClasses] = useState<ClassGroup[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    classService.list().then(setClasses).finally(() => setLoading(false));
  }, []);

  if (loading) return <Loading fullPage />;

  return (
    <div>
      <PageHeader title="Turmas" subtitle={`${classes.length} turmas cadastradas`} />
      <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {classes.map((c) => (
          <Card key={c.id} hover>
            <div className="p-5">
              <div className="flex items-start justify-between mb-3">
                <div>
                  <h2 className="font-semibold text-prismaDark-800">{c.name}</h2>
                  <p className="text-sm text-prismaGray-500">{c.year}/{c.semester} · {c.shift}</p>
                </div>
                <Badge status={c.status} />
              </div>
              <div className="text-sm text-prismaGray-600 space-y-1 mb-4">
                <p>Alunos: <span className="font-medium text-prismaDark-700">{c.studentCount}</span></p>
                <p>Freq. média: <span className="font-medium text-prismaDark-700">{c.averageAttendance ?? 0}%</span></p>
              </div>
              <Button
                variant="outline"
                size="sm"
                leftIcon={<Eye className="w-3.5 h-3.5" />}
                className="w-full"
                onClick={() => navigate(`/turmas/${c.id}`)}
              >
                Ver turma
              </Button>
            </div>
          </Card>
        ))}
      </div>
    </div>
  );
}
