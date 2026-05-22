import { useForm, useFieldArray } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { useState } from 'react';
import { CheckCircle, Send } from 'lucide-react';
import { candidateSchema, type CandidateFormData } from '../../schemas/candidateSchema';
import { candidateService } from '../../services/candidateService';
import { useToast } from '../../hooks/useToast';
import Input from '../../components/common/Input';
import Select from '../../components/common/Select';
import Textarea from '../../components/common/Textarea';
import Button from '../../components/common/Button';

const shiftOptions = [
  { value: 'Manhã', label: 'Manhã' },
  { value: 'Tarde', label: 'Tarde' },
  { value: 'Noite', label: 'Noite' },
];

const incomeOptions = [
  { value: '1', label: 'Até 1 salário mínimo' },
  { value: '2', label: 'De 1 a 2 salários mínimos' },
  { value: '3', label: 'De 2 a 3 salários mínimos' },
  { value: '4', label: 'Acima de 3 salários mínimos' },
];

export default function CandidateApplicationPage() {
  const { addToast } = useToast();
  const [submitted, setSubmitted] = useState(false);

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<CandidateFormData>({
    resolver: zodResolver(candidateSchema),
  });

  const onSubmit = async (data: CandidateFormData) => {
    try {
      await candidateService.create(data);
      setSubmitted(true);
    } catch {
      addToast('Erro ao enviar inscrição. Tente novamente.', 'error');
    }
  };

  if (submitted) {
    return (
      <div className="max-w-lg mx-auto py-24 px-4 text-center">
        <div className="w-16 h-16 rounded-full bg-green-100 flex items-center justify-center mx-auto mb-4">
          <CheckCircle className="w-8 h-8 text-green-600" />
        </div>
        <h1 className="text-2xl font-bold text-prismaDark-900 mb-2">Inscrição enviada!</h1>
        <p className="text-prismaGray-500 mb-6">
          Sua inscrição foi recebida com sucesso. Você pode acompanhar o status pelo link enviado no seu e-mail ou pela página de status.
        </p>
        <a href="/status-inscricao" className="btn-primary inline-block">
          Ver status da inscrição
        </a>
      </div>
    );
  }

  return (
    <div className="max-w-2xl mx-auto py-12 px-4">
      <div className="text-center mb-8">
        <h1 className="text-3xl font-bold text-prismaDark-900">Inscrição — Cursinho Prisma</h1>
        <p className="text-prismaGray-500 mt-2">Preencha o formulário para se inscrever no processo seletivo</p>
      </div>

      <form onSubmit={handleSubmit(onSubmit)} className="space-y-6" noValidate>
        <div className="card p-6">
          <h2 className="font-semibold text-prismaDark-800 mb-4">Dados pessoais</h2>
          <div className="grid sm:grid-cols-2 gap-4">
            <div className="sm:col-span-2">
              <Input label="Nome completo" error={errors.fullName?.message} {...register('fullName')} />
            </div>
            <Input label="CPF" placeholder="000.000.000-00" error={errors.cpf?.message} {...register('cpf')} />
            <Input label="Data de nascimento" type="date" error={errors.birthDate?.message} {...register('birthDate')} />
            <Input label="E-mail" type="email" error={errors.email?.message} {...register('email')} />
            <Input label="Telefone" placeholder="(43) 99999-9999" error={errors.phone?.message} {...register('phone')} />
          </div>
        </div>

        <div className="card p-6">
          <h2 className="font-semibold text-prismaDark-800 mb-4">Dados acadêmicos</h2>
          <div className="grid sm:grid-cols-2 gap-4">
            <div className="sm:col-span-2">
              <Input label="Escola de origem" error={errors.school?.message} {...register('school')} />
            </div>
            <Input label="Série/Ano" placeholder="3º ano EM" error={errors.grade?.message} {...register('grade')} />
            <Select label="Período" options={shiftOptions} error={errors.shift?.message} {...register('shift')} />
            <Select label="Renda familiar" options={incomeOptions} error={errors.familyIncome?.message} {...register('familyIncome', { valueAsNumber: true })} />
          </div>
        </div>

        <div className="card p-6">
          <h2 className="font-semibold text-prismaDark-800 mb-4">Endereço</h2>
          <div className="grid sm:grid-cols-3 gap-4">
            <div className="sm:col-span-2">
              <Input label="Endereço" error={errors.address?.message} {...register('address')} />
            </div>
            <Input label="CEP" placeholder="86300-000" error={errors.cep?.message} {...register('cep')} />
            <div className="sm:col-span-2">
              <Input label="Cidade" error={errors.city?.message} {...register('city')} />
            </div>
            <Input label="Estado (UF)" placeholder="PR" maxLength={2} error={errors.state?.message} {...register('state')} />
          </div>
        </div>

        <div className="card p-6">
          <h2 className="font-semibold text-prismaDark-800 mb-4">Motivação</h2>
          <Textarea
            label="Por que deseja participar do Cursinho Prisma?"
            error={errors.motivation?.message}
            {...register('motivation')}
          />
        </div>

        <Button
          type="submit"
          variant="primary"
          size="lg"
          isLoading={isSubmitting}
          className="w-full"
          leftIcon={<Send className="w-4 h-4" />}
        >
          Enviar inscrição
        </Button>
      </form>
    </div>
  );
}
