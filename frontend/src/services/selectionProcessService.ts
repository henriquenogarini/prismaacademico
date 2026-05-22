import type { SelectionProcess } from '../types/selectionProcess';
import { mockSelectionProcesses } from '../mocks/selectionProcesses';
import api from '../api/axios';
import { ENDPOINTS } from '../api/endpoints';

const useMocks = () => import.meta.env.VITE_USE_MOCKS === 'true';

let processesState = [...mockSelectionProcesses];

export const selectionProcessService = {
  async list(): Promise<SelectionProcess[]> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 400));
      return [...processesState];
    }
    const res = await api.get<SelectionProcess[]>(ENDPOINTS.selectionProcesses.list);
    return res.data;
  },

  async getById(id: string): Promise<SelectionProcess> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 300));
      const found = processesState.find((p) => p.id === id);
      if (!found) throw new Error('Processo seletivo não encontrado');
      return { ...found };
    }
    const res = await api.get<SelectionProcess>(ENDPOINTS.selectionProcesses.getById(id));
    return res.data;
  },

  async create(data: Omit<SelectionProcess, 'id' | 'createdAt' | 'applicationsCount' | 'approvedCount'>): Promise<SelectionProcess> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 600));
      const newProcess: SelectionProcess = {
        ...data,
        id: `sp-${Date.now()}`,
        applicationsCount: 0,
        approvedCount: 0,
        createdAt: new Date().toISOString(),
      };
      processesState = [...processesState, newProcess];
      return newProcess;
    }
    const res = await api.post<SelectionProcess>(ENDPOINTS.selectionProcesses.create, data);
    return res.data;
  },

  async update(id: string, data: Partial<SelectionProcess>): Promise<SelectionProcess> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 500));
      processesState = processesState.map((p) =>
        p.id === id ? { ...p, ...data } : p
      );
      const updated = processesState.find((p) => p.id === id);
      if (!updated) throw new Error('Processo não encontrado');
      return updated;
    }
    const res = await api.put<SelectionProcess>(ENDPOINTS.selectionProcesses.update(id), data);
    return res.data;
  },
};
