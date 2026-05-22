import type { AttendanceFormData } from '../types/attendance';
import api from '../api/axios';
import { ENDPOINTS } from '../api/endpoints';

const useMocks = () => import.meta.env.VITE_USE_MOCKS === 'true';

export const attendanceService = {
  async saveAttendance(data: AttendanceFormData): Promise<void> {
    if (useMocks()) {
      await new Promise((r) => setTimeout(r, 600));
      return;
    }
    await api.post(ENDPOINTS.attendance.save, data);
  },
};
