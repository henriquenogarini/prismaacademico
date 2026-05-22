import { z } from 'zod';

export const teacherSchema = z.object({
  fullName: z.string().min(3, 'Nome completo é obrigatório'),
  email: z.string().email('E-mail inválido'),
  phone: z.string().min(10, 'Telefone inválido'),
  course: z.string().min(3, 'Curso/formação é obrigatório'),
  institution: z.string().min(2, 'Instituição é obrigatória'),
  subjects: z.array(z.string()).min(1, 'Selecione pelo menos uma disciplina'),
  bio: z.string().optional(),
});

export type TeacherFormData = z.infer<typeof teacherSchema>;
