import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Eye } from 'lucide-react';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Badge from '../../components/common/Badge';
import Button from '../../components/common/Button';
import Loading from '../../components/common/Loading';
import { studentService } from '../../services/studentService';
import type { Student } from '../../types/student';

export default function StudentsPage() {
  const navigate = useNavigate();
  const [students, setStudents] = useState<Student[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    studentService.list().then(setStudents).finally(() => setLoading(false));
  }, []);

  if (loading) return <Loading fullPage />;

  return (
    <div>
      <PageHeader title="Alunos" subtitle={`${students.length} alunos cadastrados`} />
      <Card>
        <div className="overflow-x-auto">
          <table className="w-full text-sm">
            <thead>
              <tr className="border-b border-prismaGray-100">
                <th className="text-left px-4 py-3 text-xs font-semibold text-prismaGray-500 uppercase">Aluno</th>
                <th className="text-left px-4 py-3 text-xs font-semibold text-prismaGray-500 uppercase hidden sm:table-cell">Turma</th>
                <th className="text-left px-4 py-3 text-xs font-semibold text-prismaGray-500 uppercase hidden md:table-cell">Frequência</th>
                <th className="text-left px-4 py-3 text-xs font-semibold text-prismaGray-500 uppercase">Status</th>
                <th className="px-4 py-3" />
              </tr>
            </thead>
            <tbody className="divide-y divide-prismaGray-100">
              {students.map((s) => (
                <tr key={s.id} className="hover:bg-prismaGray-50 transition-colors">
                  <td className="px-4 py-3">
                    <p className="font-medium text-prismaDark-800">{s.fullName}</p>
                    <p className="text-xs text-prismaGray-500">{s.email}</p>
                  </td>
                  <td className="px-4 py-3 text-prismaDark-600 hidden sm:table-cell">{s.classGroupId}</td>
                  <td className="px-4 py-3 hidden md:table-cell">
                    <span className={`text-sm font-medium ${(s.attendanceRate ?? 0) >= 75 ? 'text-green-700' : 'text-red-600'}`}>
                      {s.attendanceRate ?? 0}%
                    </span>
                  </td>
                  <td className="px-4 py-3"><Badge status={s.status} /></td>
                  <td className="px-4 py-3 text-right">
                    <Button variant="ghost" size="sm" leftIcon={<Eye className="w-3.5 h-3.5" />} onClick={() => navigate(`/alunos/${s.id}`)}>
                      Ver
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </Card>
    </div>
  );
}
