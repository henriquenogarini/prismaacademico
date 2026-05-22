import type { Teacher } from '../types/teacher';
import { mockTeachers } from '../mocks/teachers';
import api from '../api/axios';
import { ENDPOINTS } from '../api/endpoints';

const useMocks = () => import.meta.env.VITE_USE_MOCKS === 'true';

export const teacherService = {
  async list(): Promise<Teacher[]> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 400));
      return [...mockTeachers];
    }
    const res = await api.get<Teacher[]>(ENDPOINTS.teachers.list);
    return res.data;
  },

  async getById(id: string): Promise<Teacher> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 300));
      const found = mockTeachers.find((t) => t.id === id);
      if (!found) throw new Error('Professor não encontrado');
      return { ...found };
    }
    const res = await api.get<Teacher>(ENDPOINTS.teachers.getById(id));
    return res.data;
  },
};
