import type { Announcement } from '../types/announcement';

export const mockAnnouncements: Announcement[] = [
  {
    id: 'a-1',
    title: 'Início das aulas — Turma A e Turma B',
    content:
      'As aulas do semestre 2026/1 têm início na próxima segunda-feira, dia 03 de março. Confirme sua presença respondendo este comunicado. Os alunos devem comparecer no horário correto de sua turma (Turma A: 8h, Turma B: 13h30).',
    authorId: '2',
    authorName: 'Coordenação Prisma',
    createdAt: '2026-02-28T10:00:00Z',
    isPinned: true,
  },
  {
    id: 'a-2',
    title: 'Simulado Nacional — 15 de Junho',
    content:
      'Será realizado um simulado com questões no estilo ENEM no dia 15 de junho (sábado), das 9h às 13h. Presença altamente recomendada. O gabarito será discutido na aula seguinte.',
    authorId: '2',
    authorName: 'Coordenação Prisma',
    createdAt: '2026-06-01T10:00:00Z',
    isPinned: true,
  },
  {
    id: 'a-3',
    title: 'Material de Redação disponível',
    content:
      'A professora Camila Souza disponibilizou novos materiais de redação no sistema. Acesse a seção Materiais para fazer o download das coletâneas e das propostas de redação desta semana.',
    authorId: '3',
    authorName: 'Prof. Camila Souza',
    classGroupId: 'cg-1',
    classGroupName: 'Prisma 2026/1 — Turma A',
    createdAt: '2026-05-10T14:00:00Z',
    isPinned: false,
  },
  {
    id: 'a-4',
    title: 'Aula de Matemática remarcada',
    content:
      'A aula de Matemática prevista para quinta-feira (22/05) foi remarcada para sexta-feira (23/05), mesmo horário. Pedimos desculpas pelo transtorno.',
    authorId: '3',
    authorName: 'Prof. Rafael Martins',
    classGroupId: 'cg-2',
    classGroupName: 'Prisma 2026/1 — Turma B',
    createdAt: '2026-05-20T08:30:00Z',
    isPinned: false,
  },
  {
    id: 'a-5',
    title: 'Encerramento das inscrições — Processo 2026/2',
    content:
      'As inscrições para o Processo Seletivo Prisma 2026/2 se encerram no dia 15 de julho de 2026. Compartilhe com colegas que ainda não se inscreveram.',
    authorId: '2',
    authorName: 'Coordenação Prisma',
    createdAt: '2026-07-01T10:00:00Z',
    isPinned: false,
  },
];
