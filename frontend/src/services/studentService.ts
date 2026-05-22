import type { Student } from '../types/student';
import { mockStudents } from '../mocks/students';
import api from '../api/axios';
import { ENDPOINTS } from '../api/endpoints';

const useMocks = () => import.meta.env.VITE_USE_MOCKS === 'true';

export const studentService = {
  async list(): Promise<Student[]> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 400));
      return [...mockStudents];
    }
    const res = await api.get<Student[]>(ENDPOINTS.students.list);
    return res.data;
  },

  async getById(id: string): Promise<Student> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 300));
      const found = mockStudents.find((s) => s.id === id);
      if (!found) throw new Error('Aluno não encontrado');
      return { ...found };
    }
    const res = await api.get<Student>(ENDPOINTS.students.getById(id));
    return res.data;
  },
};
