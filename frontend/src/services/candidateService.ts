import type { Candidate, CandidateStatus } from '../types/candidate';
import { mockCandidates } from '../mocks/candidates';
import api from '../api/axios';
import { ENDPOINTS } from '../api/endpoints';

const useMocks = () => import.meta.env.VITE_USE_MOCKS === 'true';

let candidatesState = [...mockCandidates];

export const candidateService = {
  async list(): Promise<Candidate[]> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 400));
      return [...candidatesState];
    }
    const res = await api.get<Candidate[]>(ENDPOINTS.candidates.list);
    return res.data;
  },

  async getById(id: string): Promise<Candidate> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 300));
      const found = candidatesState.find((c) => c.id === id);
      if (!found) throw new Error('Candidato não encontrado');
      return { ...found };
    }
    const res = await api.get<Candidate>(ENDPOINTS.candidates.getById(id));
    return res.data;
  },

  async create(data: Record<string, unknown>): Promise<Candidate> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 600));
      const newCandidate = {
        ...data,
        id: `c-${Date.now()}`,
        appliedAt: new Date().toISOString(),
        status: 'SUBMITTED' as const,
        statusHistory: [
          {
            id: `sh-${Date.now()}`,
            status: 'SUBMITTED' as const,
            changedAt: new Date().toISOString(),
            notes: 'Inscrição enviada pelo formulário.',
          },
        ],
      } as Candidate;
      candidatesState = [...candidatesState, newCandidate];
      return newCandidate;
    }
    const res = await api.post<Candidate>(ENDPOINTS.candidates.create, data);
    return res.data;
  },

  async updateStatus(id: string, status: CandidateStatus, notes?: string): Promise<Candidate> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 400));
      candidatesState = candidatesState.map((c) => {
        if (c.id !== id) return c;
        return {
          ...c,
          status,
          statusHistory: [
            ...c.statusHistory,
            { id: `sh-${Date.now()}`, status, changedAt: new Date().toISOString(), notes },
          ],
        };
      });
      const updated = candidatesState.find((c) => c.id === id);
      if (!updated) throw new Error('Candidato não encontrado');
      return updated;
    }
    const res = await api.patch<Candidate>(ENDPOINTS.candidates.updateStatus(id), {
      status,
      notes,
    });
    return res.data;
  },
};
