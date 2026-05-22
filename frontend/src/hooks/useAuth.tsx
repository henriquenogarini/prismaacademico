import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';
import type { User, UserRole } from '../types/user';
import type { AuthContextType } from '../types/auth';

const STORAGE_KEY_TOKEN = '@prisma:token';
const STORAGE_KEY_USER = '@prisma:user';

const MOCK_USERS: (User & { password: string })[] = [
  {
    id: '1',
    name: 'Admin Prisma',
    email: 'admin@prisma.com',
    password: '123456',
    role: 'ADMIN',
  },
  {
    id: '2',
    name: 'Coordenação Prisma',
    email: 'coordination@prisma.com',
    password: '123456',
    role: 'COORDINATION',
  },
  {
    id: '3',
    name: 'Prof. Camila Souza',
    email: 'teacher@prisma.com',
    password: '123456',
    role: 'TEACHER',
  },
  {
    id: '4',
    name: 'Ana Clara Santos',
    email: 'student@prisma.com',
    password: '123456',
    role: 'STUDENT',
  },
  {
    id: '5',
    name: 'João Pedro Almeida',
    email: 'candidate@prisma.com',
    password: '123456',
    role: 'CANDIDATE',
  },
];

const DEMO_ACCOUNTS: Record<string, string> = {
  ADMIN: 'admin@prisma.com',
  COORDINATION: 'coordination@prisma.com',
  TEACHER: 'teacher@prisma.com',
  STUDENT: 'student@prisma.com',
  CANDIDATE: 'candidate@prisma.com',
};

export const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const storedToken = localStorage.getItem(STORAGE_KEY_TOKEN);
    const storedUser = localStorage.getItem(STORAGE_KEY_USER);
    if (storedToken && storedUser) {
      try {
        setToken(storedToken);
        setUser(JSON.parse(storedUser) as User);
      } catch {
        localStorage.removeItem(STORAGE_KEY_TOKEN);
        localStorage.removeItem(STORAGE_KEY_USER);
      }
    }
    setIsLoading(false);
  }, []);

  const persistAuth = useCallback((userData: User, tokenValue: string) => {
    setUser(userData);
    setToken(tokenValue);
    localStorage.setItem(STORAGE_KEY_TOKEN, tokenValue);
    localStorage.setItem(STORAGE_KEY_USER, JSON.stringify(userData));
  }, []);

  const login = useCallback(
    async (email: string, password: string): Promise<void> => {
      const useMocks = import.meta.env.VITE_USE_MOCKS === 'true';

      if (useMocks) {
        await new Promise((res) => setTimeout(res, 600));
        const found = MOCK_USERS.find(
          (u) => u.email === email && u.password === password
        );
        if (!found) {
          throw new Error('E-mail ou senha incorretos.');
        }
        const { password: _pw, ...userData } = found;
        void _pw;
        const fakeToken = `mock-token-${userData.id}-${Date.now()}`;
        persistAuth(userData, fakeToken);
        return;
      }

      const { default: api } = await import('../api/axios');
      const response = await api.post<{ token: string; user: User }>('/auth/login', {
        email,
        password,
      });
      persistAuth(response.data.user, response.data.token);
    },
    [persistAuth]
  );

  const loginAsDemo = useCallback(
    (role: string) => {
      const email = DEMO_ACCOUNTS[role as UserRole];
      if (!email) return;
      const found = MOCK_USERS.find((u) => u.email === email);
      if (!found) return;
      const { password: _pw, ...userData } = found;
      void _pw;
      const fakeToken = `mock-token-${userData.id}-${Date.now()}`;
      persistAuth(userData, fakeToken);
    },
    [persistAuth]
  );

  const logout = useCallback(() => {
    setUser(null);
    setToken(null);
    localStorage.removeItem(STORAGE_KEY_TOKEN);
    localStorage.removeItem(STORAGE_KEY_USER);
  }, []);

  const value: AuthContextType = {
    user,
    token,
    isAuthenticated: !!user && !!token,
    isLoading,
    login,
    loginAsDemo,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth(): AuthContextType {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error('useAuth deve ser usado dentro do AuthProvider');
  }
  return ctx;
}
