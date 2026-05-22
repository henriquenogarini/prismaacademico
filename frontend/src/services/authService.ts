import api from '../api/axios';
import { ENDPOINTS } from '../api/endpoints';
import type { User } from '../types/user';

export const authService = {
  async login(email: string, password: string): Promise<{ token: string; user: User }> {
    const res = await api.post<{ token: string; user: User }>(ENDPOINTS.auth.login, {
      email,
      password,
    });
    return res.data;
  },

  async logout(): Promise<void> {
    await api.post(ENDPOINTS.auth.logout);
  },

  async getMe(): Promise<User> {
    const res = await api.get<User>(ENDPOINTS.auth.me);
    return res.data;
  },
};
