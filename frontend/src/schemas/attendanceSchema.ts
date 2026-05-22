import { z } from 'zod';

const attendanceRecordSchema = z.object({
  studentId: z.string(),
  studentName: z.string(),
  status: z.enum(['PRESENT', 'ABSENT', 'JUSTIFIED']),
});

export const attendanceSchema = z.object({
  classGroupId: z.string().min(1, 'Turma é obrigatória'),
  subject: z.string().min(1, 'Disciplina é obrigatória'),
  date: z.string().min(1, 'Data da aula é obrigatória'),
  records: z.array(attendanceRecordSchema).min(1, 'Registre a frequência de pelo menos um aluno'),
});

export type AttendanceFormData = z.infer<typeof attendanceSchema>;
