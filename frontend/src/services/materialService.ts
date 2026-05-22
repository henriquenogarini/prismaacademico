import type { Material } from '../types/material';
import { mockMaterials } from '../mocks/materials';
import api from '../api/axios';
import { ENDPOINTS } from '../api/endpoints';

const useMocks = () => import.meta.env.VITE_USE_MOCKS === 'true';

export const materialService = {
  async list(): Promise<Material[]> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 400));
      return [...mockMaterials];
    }
    const res = await api.get<Material[]>(ENDPOINTS.materials.list);
    return res.data;
  },
};
