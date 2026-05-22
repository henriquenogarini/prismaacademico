import type { SelectionProcess } from '../types/selectionProcess';

export const mockSelectionProcesses: SelectionProcess[] = [
  {
    id: 'sp-1',
    title: 'Processo Seletivo Prisma 2026/1',
    year: 2026,
    semester: 1,
    startDate: '2026-01-15',
    endDate: '2026-02-15',
    vacancies: 60,
    description:
      'Processo seletivo para ingresso nas turmas do primeiro semestre de 2026 do Cursinho Comunitário Prisma. Destinado a estudantes de escola pública do município de Cornélio Procópio e região.',
    status: 'FINISHED',
    applicationsCount: 87,
    approvedCount: 60,
    createdAt: '2026-01-10T10:00:00Z',
  },
  {
    id: 'sp-2',
    title: 'Processo Seletivo Prisma 2026/2',
    year: 2026,
    semester: 2,
    startDate: '2026-06-01',
    endDate: '2026-07-15',
    vacancies: 60,
    description:
      'Processo seletivo para ingresso nas turmas do segundo semestre de 2026. Vagas limitadas para alunos de escolas públicas estaduais e municipais.',
    status: 'OPEN',
    applicationsCount: 42,
    approvedCount: 0,
    createdAt: '2026-05-20T10:00:00Z',
  },
  {
    id: 'sp-3',
    title: 'Processo Seletivo Prisma 2025/2',
    year: 2025,
    semester: 2,
    startDate: '2025-06-01',
    endDate: '2025-07-10',
    vacancies: 50,
    description:
      'Processo seletivo do segundo semestre de 2025.',
    status: 'FINISHED',
    applicationsCount: 73,
    approvedCount: 50,
    createdAt: '2025-05-15T10:00:00Z',
  },
];
