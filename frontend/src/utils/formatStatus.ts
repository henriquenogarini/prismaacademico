export type StatusKey =
  | 'SUBMITTED'
  | 'UNDER_REVIEW'
  | 'PENDING'
  | 'APPROVED'
  | 'REJECTED'
  | 'ACTIVE'
  | 'INACTIVE'
  | 'DROPOUT'
  | 'COMPLETED'
  | 'OPEN'
  | 'CLOSED'
  | 'DRAFT'
  | 'FINISHED'
  | 'PRESENT'
  | 'ABSENT'
  | 'JUSTIFIED'
  | 'MORNING'
  | 'AFTERNOON'
  | 'EVENING';

export interface StatusConfig {
  label: string;
  color: string;
  bgColor: string;
  textColor: string;
}

const statusMap: Record<string, StatusConfig> = {
  SUBMITTED: {
    label: 'Enviada',
    color: 'blue',
    bgColor: 'bg-blue-100',
    textColor: 'text-blue-800',
  },
  UNDER_REVIEW: {
    label: 'Em análise',
    color: 'amber',
    bgColor: 'bg-amber-100',
    textColor: 'text-amber-800',
  },
  PENDING: {
    label: 'Pendente',
    color: 'orange',
    bgColor: 'bg-orange-100',
    textColor: 'text-orange-800',
  },
  APPROVED: {
    label: 'Aprovada',
    color: 'green',
    bgColor: 'bg-green-100',
    textColor: 'text-green-800',
  },
  REJECTED: {
    label: 'Reprovada',
    color: 'red',
    bgColor: 'bg-red-100',
    textColor: 'text-red-800',
  },
  ACTIVE: {
    label: 'Ativo',
    color: 'green',
    bgColor: 'bg-green-100',
    textColor: 'text-green-800',
  },
  INACTIVE: {
    label: 'Inativo',
    color: 'gray',
    bgColor: 'bg-gray-100',
    textColor: 'text-gray-600',
  },
  DROPOUT: {
    label: 'Desistente',
    color: 'red',
    bgColor: 'bg-red-100',
    textColor: 'text-red-700',
  },
  COMPLETED: {
    label: 'Concluinte',
    color: 'indigo',
    bgColor: 'bg-indigo-100',
    textColor: 'text-indigo-800',
  },
  OPEN: {
    label: 'Aberto',
    color: 'green',
    bgColor: 'bg-green-100',
    textColor: 'text-green-800',
  },
  CLOSED: {
    label: 'Encerrado',
    color: 'gray',
    bgColor: 'bg-gray-100',
    textColor: 'text-gray-600',
  },
  DRAFT: {
    label: 'Rascunho',
    color: 'slate',
    bgColor: 'bg-slate-100',
    textColor: 'text-slate-600',
  },
  FINISHED: {
    label: 'Finalizado',
    color: 'blue',
    bgColor: 'bg-blue-100',
    textColor: 'text-blue-700',
  },
  PRESENT: {
    label: 'Presente',
    color: 'green',
    bgColor: 'bg-green-100',
    textColor: 'text-green-800',
  },
  ABSENT: {
    label: 'Ausente',
    color: 'red',
    bgColor: 'bg-red-100',
    textColor: 'text-red-700',
  },
  JUSTIFIED: {
    label: 'Justificado',
    color: 'amber',
    bgColor: 'bg-amber-100',
    textColor: 'text-amber-800',
  },
  MORNING: {
    label: 'Matutino',
    color: 'cyan',
    bgColor: 'bg-cyan-100',
    textColor: 'text-cyan-800',
  },
  AFTERNOON: {
    label: 'Vespertino',
    color: 'orange',
    bgColor: 'bg-orange-100',
    textColor: 'text-orange-700',
  },
  EVENING: {
    label: 'Noturno',
    color: 'indigo',
    bgColor: 'bg-indigo-100',
    textColor: 'text-indigo-800',
  },
};

export function getStatusConfig(status: string): StatusConfig {
  return (
    statusMap[status] ?? {
      label: status,
      color: 'gray',
      bgColor: 'bg-gray-100',
      textColor: 'text-gray-600',
    }
  );
}

export function formatStatus(status: string): string {
  return getStatusConfig(status).label;
}
