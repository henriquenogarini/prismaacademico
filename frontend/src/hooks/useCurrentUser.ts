import { useAuth } from './useAuth';
import type { User } from '../types/user';

export function useCurrentUser(): User | null {
  const { user } = useAuth();
  return user;
}
