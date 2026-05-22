import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { attendanceSchema, type AttendanceFormData } from '../../schemas/attendanceSchema';
import { attendanceService } from '../../services/attendanceService';
import { classService } from '../../services/classService';
import { studentService } from '../../services/studentService';
import { useToast } from '../../hooks/useToast';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Select from '../../components/common/Select';
import Input from '../../components/common/Input';
import Button from '../../components/common/Button';
import Loading from '../../components/common/Loading';
import type { ClassGroup } from '../../types/classGroup';
import type { Student } from '../../types/student';

const statusOptions = [
  { value: 'PRESENT', label: 'Presente' },
  { value: 'ABSENT', label: 'Ausente' },
  { value: 'JUSTIFIED', label: 'Justificado' },
];

const subjectOptions = [
  'Matemática', 'Português', 'Física', 'Química', 'Biologia',
  'História', 'Geografia', 'Inglês', 'Redação', 'Filosofia',
].map((s) => ({ value: s, label: s }));

export default function AttendancePage() {
  const { addToast } = useToast();
  const [classes, setClasses] = useState<ClassGroup[]>([]);
  const [students, setStudents] = useState<Student[]>([]);
  const [selectedClass, setSelectedClass] = useState('');
  const [loading, setLoading] = useState(true);
  const [submitted, setSubmitted] = useState(false);

  const classOptions = classes.map((c) => ({ value: c.id, label: c.name }));
  const classStudents = students.filter((s) => s.classGroupId === selectedClass);

  useEffect(() => {
    Promise.all([classService.list(), studentService.list()]).then(([cls, sts]) => {
      setClasses(cls);
      setStudents(sts);
    }).finally(() => setLoading(false));
  }, []);

  const {
    register,
    handleSubmit,
    setValue,
    watch,
    formState: { errors, isSubmitting },
  } = useForm<AttendanceFormData>({
    resolver: zodResolver(attendanceSchema),
    defaultValues: { records: [] },
  });

  const records = watch('records');

  const handleClassChange = (classId: string) => {
    setSelectedClass(classId);
    setValue('classGroupId', classId);
    const sts = students.filter((s) => s.classGroupId === classId);
    setValue('records', sts.map((s) => ({ studentId: s.id, studentName: s.fullName, status: 'PRESENT' as const })));
  };

  const onSubmit = async (data: AttendanceFormData) => {
    try {
      await attendanceService.saveAttendance(data);
      setSubmitted(true);
      addToast('Frequência registrada com sucesso!', 'success');
    } catch {
      addToast('Erro ao salvar frequência.', 'error');
    }
  };

  if (loading) return <Loading fullPage />;

  return (
    <div>
      <PageHeader title="Registro de Frequência" subtitle="Registre a presença dos alunos por aula" />

      {submitted ? (
        <Card>
          <div className="p-8 text-center">
            <p className="text-lg font-semibold text-green-700">Frequência registrada com sucesso!</p>
            <Button variant="outline" className="mt-4" onClick={() => setSubmitted(false)}>
              Registrar nova aula
            </Button>
          </div>
        </Card>
      ) : (
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <Card>
            <div className="p-5 grid sm:grid-cols-3 gap-4">
              <Select
                label="Turma"
                options={classOptions}
                placeholder="Selecione a turma"
                error={errors.classGroupId?.message}
                onChange={(e) => handleClassChange(e.target.value)}
              />
              <Select
                label="Disciplina"
                options={subjectOptions}
                placeholder="Selecione a disciplina"
                error={errors.subject?.message}
                {...register('subject')}
              />
              <Input
                label="Data da aula"
                type="date"
                error={errors.date?.message}
                {...register('date')}
              />
            </div>
          </Card>

          {classStudents.length > 0 && (
            <Card>
              <div className="p-5">
                <h2 className="font-semibold text-prismaDark-800 mb-4">
                  Alunos ({classStudents.length})
                </h2>
                <div className="space-y-3">
                  {records.map((rec, idx) => (
                    <div key={rec.studentId} className="flex items-center justify-between py-2 border-b border-prismaGray-100 last:border-0">
                      <span className="text-sm text-prismaDark-800">{rec.studentName}</span>
                      <div className="flex gap-2">
                        {statusOptions.map((opt) => (
                          <label key={opt.value} className="flex items-center gap-1 cursor-pointer">
                            <input
                              type="radio"
                              value={opt.value}
                              {...register(`records.${idx}.status`)}
                              defaultChecked={opt.value === 'PRESENT'}
                              className="accent-prismaBlue-600"
                            />
                            <span className="text-xs text-prismaGray-600">{opt.label}</span>
                          </label>
                        ))}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </Card>
          )}

          {classStudents.length > 0 && (
            <Button type="submit" variant="primary" isLoading={isSubmitting} className="w-full sm:w-auto">
              Salvar frequência
            </Button>
          )}
        </form>
      )}
    </div>
  );
}
