import type { Announcement } from '../types/announcement';
import { mockAnnouncements } from '../mocks/announcements';
import api from '../api/axios';
import { ENDPOINTS } from '../api/endpoints';

const useMocks = () => import.meta.env.VITE_USE_MOCKS === 'true';

export const announcementService = {
  async list(): Promise<Announcement[]> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 400));
      return [...mockAnnouncements];
    }
    const res = await api.get<Announcement[]>(ENDPOINTS.announcements.list);
    return res.data;
  },
};
