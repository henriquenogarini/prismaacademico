import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { Navigate } from 'react-router-dom';
import { loginSchema, type LoginFormData } from '../../schemas/loginSchema';
import { useAuth } from '../../hooks/useAuth';
import { useToast } from '../../hooks/useToast';
import Input from '../../components/common/Input';
import Button from '../../components/common/Button';
import { getDefaultRoute } from '../../utils/permissions';

const demoAccounts = [
  { role: 'ADMIN' as const, label: 'Administrador', color: 'from-prismaBlue-600 to-prismaBlue-700' },
  { role: 'COORDINATION' as const, label: 'Coordenação', color: 'from-prismaIndigo-600 to-prismaIndigo-700' },
  { role: 'TEACHER' as const, label: 'Professor(a)', color: 'from-prismaCyan-600 to-prismaCyan-700' },
  { role: 'STUDENT' as const, label: 'Aluno(a)', color: 'from-green-600 to-green-700' },
];

export default function LoginPage() {
  const { login, loginAsDemo, isAuthenticated, user } = useAuth();
  const { addToast } = useToast();

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
    setError,
  } = useForm<LoginFormData>({
    resolver: zodResolver(loginSchema),
  });

  if (isAuthenticated && user) {
    return <Navigate to={getDefaultRoute(user.role)} replace />;
  }

  const onSubmit = async (data: LoginFormData) => {
    try {
      await login(data.email, data.password);
      addToast('Bem-vindo(a) ao Prisma Acadêmico!', 'success');
    } catch {
      setError('root', { message: 'E-mail ou senha inválidos.' });
      addToast('Credenciais inválidas.', 'error');
    }
  };

  const handleDemoLogin = async (role: typeof demoAccounts[number]['role']) => {
    try {
      await loginAsDemo(role);
      addToast('Acesso demonstração ativado!', 'success');
    } catch {
      addToast('Erro ao acessar conta demo.', 'error');
    }
  };

  return (
    <div className="w-full max-w-sm">
      <div className="card card-md shadow-prisma-lg">
        <div className="text-center mb-6">
          <h1 className="text-2xl font-bold text-prismaDark-900">Bem-vindo(a)!</h1>
          <p className="text-sm text-prismaGray-500 mt-1">Entre com suas credenciais para acessar</p>
        </div>

        <form onSubmit={handleSubmit(onSubmit)} noValidate className="space-y-4">
          <Input
            label="E-mail"
            type="email"
            autoComplete="email"
            placeholder="seu@email.com"
            error={errors.email?.message}
            {...register('email')}
          />
          <Input
            label="Senha"
            type="password"
            autoComplete="current-password"
            placeholder="••••••"
            error={errors.password?.message}
            {...register('password')}
          />

          {errors.root && (
            <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-xl px-3 py-2">
              {errors.root.message}
            </p>
          )}

          <Button
            type="submit"
            variant="primary"
            size="lg"
            isLoading={isSubmitting}
            className="w-full"
          >
            Entrar
          </Button>

          <button type="button" className="w-full text-xs text-center text-prismaGray-500 hover:text-prismaBlue-700 transition-colors">
            Esqueci minha senha
          </button>
        </form>

        <div className="mt-6 pt-5 border-t border-prismaGray-100">
          <p className="text-xs font-medium text-prismaGray-500 text-center mb-3">
            Acesso demonstração
          </p>
          <div className="grid grid-cols-2 gap-2">
            {demoAccounts.map((acc) => (
              <button
                key={acc.role}
                type="button"
                onClick={() => handleDemoLogin(acc.role)}
                className={`text-xs py-2 px-3 rounded-xl text-white font-medium bg-gradient-to-r ${acc.color} hover:opacity-90 transition-opacity`}
              >
                {acc.label}
              </button>
            ))}
          </div>
        </div>
      </div>

      <p className="text-center text-xs text-white/60 mt-4">
        Cursinho Comunitário Prisma · UTFPR-CP
      </p>
    </div>
  );
}
