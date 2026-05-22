import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { selectionProcessSchema, type SelectionProcessFormData } from '../../schemas/selectionProcessSchema';
import { selectionProcessService } from '../../services/selectionProcessService';
import { useToast } from '../../hooks/useToast';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Input from '../../components/common/Input';
import Select from '../../components/common/Select';
import Textarea from '../../components/common/Textarea';
import Button from '../../components/common/Button';
import Loading from '../../components/common/Loading';

const statusOptions = [
  { value: 'DRAFT', label: 'Rascunho' },
  { value: 'OPEN', label: 'Aberto' },
  { value: 'CLOSED', label: 'Encerrado' },
  { value: 'FINISHED', label: 'Finalizado' },
];

const semesterOptions = [
  { value: '1', label: '1º semestre' },
  { value: '2', label: '2º semestre' },
];

export default function SelectionProcessFormPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { addToast } = useToast();
  const isEditing = Boolean(id);
  const [loading, setLoading] = useState(isEditing);

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<SelectionProcessFormData>({
    resolver: zodResolver(selectionProcessSchema),
  });

  useEffect(() => {
    if (isEditing && id) {
      selectionProcessService.getById(id).then((p) => {
        if (p) {
          reset({
            title: p.title,
            year: p.year,
            semester: p.semester,
            startDate: p.startDate.slice(0, 10),
            endDate: p.endDate.slice(0, 10),
            vacancies: p.vacancies,
            description: p.description ?? '',
            status: p.status,
          });
        }
        setLoading(false);
      });
    }
  }, [id, isEditing, reset]);

  const onSubmit = async (data: SelectionProcessFormData) => {
    try {
      if (isEditing && id) {
        await selectionProcessService.update(id, data);
        addToast('Processo atualizado com sucesso!', 'success');
      } else {
        await selectionProcessService.create(data);
        addToast('Processo criado com sucesso!', 'success');
      }
      navigate('/processos-seletivos');
    } catch {
      addToast('Erro ao salvar processo.', 'error');
    }
  };

  if (loading) return <Loading fullPage />;

  return (
    <div>
      <PageHeader
        title={isEditing ? 'Editar Processo Seletivo' : 'Novo Processo Seletivo'}
        breadcrumbs={[
          { label: 'Processos Seletivos', href: '/processos-seletivos' },
          { label: isEditing ? 'Editar' : 'Novo' },
        ]}
      />

      <Card>
        <form onSubmit={handleSubmit(onSubmit)} className="p-6 space-y-5">
          <div className="grid sm:grid-cols-3 gap-4">
            <div className="sm:col-span-3">
              <Input
                label="Título"
                placeholder="Ex: Processo Seletivo 2026/1"
                error={errors.title?.message}
                {...register('title')}
              />
            </div>
            <Input
              label="Ano"
              type="number"
              placeholder="2026"
              error={errors.year?.message}
              {...register('year', { valueAsNumber: true })}
            />
            <Select
              label="Semestre"
              options={semesterOptions}
              error={errors.semester?.message}
              {...register('semester', { valueAsNumber: true })}
            />
            <Input
              label="Vagas"
              type="number"
              placeholder="50"
              error={errors.vacancies?.message}
              {...register('vacancies', { valueAsNumber: true })}
            />
            <Input
              label="Início das inscrições"
              type="date"
              error={errors.startDate?.message}
              {...register('startDate')}
            />
            <Input
              label="Fim das inscrições"
              type="date"
              error={errors.endDate?.message}
              {...register('endDate')}
            />
            <Select
              label="Status"
              options={statusOptions}
              error={errors.status?.message}
              {...register('status')}
            />
            <Input
              label="Data da prova (opcional)"
              type="date"
              {...register('description')}
            />
            <div className="sm:col-span-3">
              <Textarea
                label="Descrição (opcional)"
                placeholder="Informações adicionais sobre o processo seletivo..."
                error={errors.description?.message}
                {...register('description')}
              />
            </div>
          </div>

          <div className="flex gap-3 pt-2">
            <Button
              type="button"
              variant="outline"
              onClick={() => navigate('/processos-seletivos')}
            >
              Cancelar
            </Button>
            <Button type="submit" variant="primary" isLoading={isSubmitting}>
              {isEditing ? 'Salvar alterações' : 'Criar processo'}
            </Button>
          </div>
        </form>
      </Card>
    </div>
  );
}
