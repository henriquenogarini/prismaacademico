import type { ClassGroup } from '../types/classGroup';
import { mockClasses } from '../mocks/classes';
import api from '../api/axios';
import { ENDPOINTS } from '../api/endpoints';

const useMocks = () => import.meta.env.VITE_USE_MOCKS === 'true';

export const classService = {
  async list(): Promise<ClassGroup[]> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 400));
      return [...mockClasses];
    }
    const res = await api.get<ClassGroup[]>(ENDPOINTS.classes.list);
    return res.data;
  },

  async getById(id: string): Promise<ClassGroup> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 300));
      const found = mockClasses.find((c) => c.id === id);
      if (!found) throw new Error('Turma não encontrada');
      return { ...found };
    }
    const res = await api.get<ClassGroup>(ENDPOINTS.classes.getById(id));
    return res.data;
  },
};
