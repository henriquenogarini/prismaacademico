import { z } from 'zod';

export const candidateSchema = z.object({
  fullName: z.string().min(3, 'Nome completo é obrigatório (mínimo 3 caracteres)'),
  cpf: z
    .string()
    .min(11, 'CPF deve ter 11 dígitos')
    .regex(/^\d{3}\.?\d{3}\.?\d{3}-?\d{2}$/, 'CPF inválido'),
  birthDate: z.string().min(1, 'Data de nascimento é obrigatória'),
  phone: z.string().min(10, 'Telefone inválido'),
  email: z.string().email('E-mail inválido'),
  school: z.string().min(3, 'Nome da escola é obrigatório'),
  grade: z.string().min(1, 'Ano/série é obrigatório'),
  shift: z.string().min(1, 'Período é obrigatório'),
  familyIncome: z.number().min(1, 'Renda familiar é obrigatória'),
  address: z.string().min(3, 'Endereço é obrigatório'),
  cep: z.string().min(8, 'CEP inválido'),
  city: z.string().min(2, 'Cidade é obrigatória'),
  state: z.string().length(2, 'UF deve ter 2 caracteres'),
  motivation: z.string().min(20, 'Descreva sua motivação (mínimo 20 caracteres)'),
});

export type CandidateFormData = z.infer<typeof candidateSchema>;
