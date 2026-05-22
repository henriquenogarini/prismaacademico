import { useEffect, useState } from 'react';
import { BookOpen, CalendarCheck, Bell } from 'lucide-react';
import { useAuth } from '../../hooks/useAuth';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Badge from '../../components/common/Badge';
import Loading from '../../components/common/Loading';
import { studentService } from '../../services/studentService';
import { announcementService } from '../../services/announcementService';
import type { Student } from '../../types/student';
import type { Announcement } from '../../types/announcement';
import { formatRelativeDate } from '../../utils/formatDate';

export default function StudentAreaPage() {
  const { user } = useAuth();
  const [student, setStudent] = useState<Student | null>(null);
  const [announcements, setAnnouncements] = useState<Announcement[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      studentService.list().then((list) => list.find((s) => s.email === user?.email) ?? null),
      announcementService.list(),
    ]).then(([s, ann]) => {
      setStudent(s);
      setAnnouncements(ann.slice(0, 3));
    }).finally(() => setLoading(false));
  }, [user]);

  if (loading) return <Loading fullPage />;

  return (
    <div>
      <PageHeader
        title={`Olá, ${user?.name.split(' ')[0]}!`}
        subtitle="Sua área pessoal no Cursinho Prisma"
      />

      <div className="grid lg:grid-cols-3 gap-4">
        <div className="lg:col-span-2 grid sm:grid-cols-2 gap-4">
          <Card>
            <div className="p-5 flex items-center gap-4">
              <div className="w-12 h-12 rounded-xl bg-prismaBlue-50 flex items-center justify-center">
                <CalendarCheck className="w-6 h-6 text-prismaBlue-700" />
              </div>
              <div>
                <p className="text-sm text-prismaGray-500">Frequência</p>
                <p className="text-2xl font-bold text-prismaDark-900">{student?.attendanceRate ?? 0}%</p>
              </div>
            </div>
          </Card>
          <Card>
            <div className="p-5 flex items-center gap-4">
              <div className="w-12 h-12 rounded-xl bg-green-50 flex items-center justify-center">
                <BookOpen className="w-6 h-6 text-green-700" />
              </div>
              <div>
                <p className="text-sm text-prismaGray-500">Turma</p>
                <p className="text-lg font-bold text-prismaDark-900">{student?.classGroupId ?? '—'}</p>
              </div>
            </div>
          </Card>

          {student && (
            <Card className="sm:col-span-2">
              <div className="p-5">
                <h2 className="font-semibold text-prismaDark-800 mb-3">Seus dados</h2>
                <dl className="grid sm:grid-cols-2 gap-3 text-sm">
                  {[
                    ['Matrícula', student.enrollmentCode ?? '—'],
                    ['E-mail', student.email],
                    ['Status', ''],
                  ].map(([label, value]) => (
                    <div key={label}>
                      <dt className="text-xs font-medium text-prismaGray-500 uppercase">{label}</dt>
                      <dd>
                        {label === 'Status' ? <Badge status={student.status} /> : value}
                      </dd>
                    </div>
                  ))}
                </dl>
              </div>
            </Card>
          )}
        </div>

        <Card>
          <div className="p-5">
            <div className="flex items-center gap-2 mb-4">
              <Bell className="w-4 h-4 text-prismaBlue-700" />
              <h2 className="font-semibold text-prismaDark-800">Comunicados recentes</h2>
            </div>
            {announcements.length === 0 ? (
              <p className="text-sm text-prismaGray-500">Nenhum comunicado.</p>
            ) : (
              <ul className="space-y-3">
                {announcements.map((a) => (
                  <li key={a.id} className="border-b border-prismaGray-100 pb-3 last:border-0">
                    <p className="text-sm font-medium text-prismaDark-800">{a.title}</p>
                    <p className="text-xs text-prismaGray-500 mt-0.5">{formatRelativeDate(a.createdAt)}</p>
                  </li>
                ))}
              </ul>
            )}
          </div>
        </Card>
      </div>
    </div>
  );
}
