import { z } from 'zod';

export const selectionProcessSchema = z.object({
  title: z.string().min(5, 'Título deve ter no mínimo 5 caracteres'),
  year: z
    .number({ invalid_type_error: 'Ano é obrigatório' })
    .int()
    .min(2020, 'Ano inválido')
    .max(2100, 'Ano inválido'),
  semester: z
    .number({ invalid_type_error: 'Semestre é obrigatório' })
    .int()
    .min(1)
    .max(2),
  startDate: z.string().min(1, 'Data de início é obrigatória'),
  endDate: z.string().min(1, 'Data de término é obrigatória'),
  vacancies: z
    .number({ invalid_type_error: 'Número de vagas é obrigatório' })
    .int()
    .min(1, 'Mínimo de 1 vaga')
    .max(500),
  description: z.string().optional(),
  status: z.enum(['DRAFT', 'OPEN', 'CLOSED', 'FINISHED']),
});

export type SelectionProcessFormData = z.infer<typeof selectionProcessSchema>;
