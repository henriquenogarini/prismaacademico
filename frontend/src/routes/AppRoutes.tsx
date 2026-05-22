import { lazy, Suspense } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Loading from '../components/common/Loading';
import ProtectedRoute from './ProtectedRoute';
import PublicLayout from '../layouts/PublicLayout';
import AuthLayout from '../layouts/AuthLayout';
import DashboardLayout from '../layouts/DashboardLayout';
import { useAuth } from '../hooks/useAuth';
import { getDefaultRoute } from '../utils/permissions';

const LandingPage = lazy(() => import('../pages/public/LandingPage'));
const LoginPage = lazy(() => import('../pages/auth/LoginPage'));

const CoordinationDashboardPage = lazy(() => import('../pages/dashboard/CoordinationDashboardPage'));
const SelectionProcessesPage = lazy(() => import('../pages/selection-processes/SelectionProcessesPage'));
const SelectionProcessFormPage = lazy(() => import('../pages/selection-processes/SelectionProcessFormPage'));
const CandidateApplicationPage = lazy(() => import('../pages/applications/CandidateApplicationPage'));
const ApplicationStatusPage = lazy(() => import('../pages/applications/ApplicationStatusPage'));
const ApplicationsListPage = lazy(() => import('../pages/applications/ApplicationsListPage'));
const CandidateDetailsPage = lazy(() => import('../pages/applications/CandidateDetailsPage'));
const StudentsPage = lazy(() => import('../pages/students/StudentsPage'));
const StudentDetailsPage = lazy(() => import('../pages/students/StudentDetailsPage'));
const ClassesPage = lazy(() => import('../pages/classes/ClassesPage'));
const ClassDetailsPage = lazy(() => import('../pages/classes/ClassDetailsPage'));
const TeachersPage = lazy(() => import('../pages/teachers/TeachersPage'));
const AttendancePage = lazy(() => import('../pages/attendance/AttendancePage'));
const StudentAreaPage = lazy(() => import('../pages/student-area/StudentAreaPage'));
const MaterialsPage = lazy(() => import('../pages/materials/MaterialsPage'));
const AnnouncementsPage = lazy(() => import('../pages/announcements/AnnouncementsPage'));
const ReportsPage = lazy(() => import('../pages/reports/ReportsPage'));
const NotFoundPage = lazy(() => import('../pages/errors/NotFoundPage'));
const AccessDeniedPage = lazy(() => import('../pages/errors/AccessDeniedPage'));

function AuthRedirect({ children }: { children: React.ReactNode }) {
  const { isAuthenticated, user } = useAuth();
  if (isAuthenticated && user) {
    return <Navigate to={getDefaultRoute(user.role)} replace />;
  }
  return <>{children}</>;
}

export default function AppRoutes() {
  return (
    <Suspense fallback={<Loading fullPage message="Carregando..." />}>
      <Routes>
        <Route element={<PublicLayout />}>
          <Route path="/" element={<LandingPage />} />
          <Route path="/inscricao" element={<CandidateApplicationPage />} />
          <Route path="/status-inscricao" element={<ApplicationStatusPage />} />
        </Route>

        <Route element={<AuthLayout />}>
          <Route
            path="/login"
            element={
              <AuthRedirect>
                <LoginPage />
              </AuthRedirect>
            }
          />
        </Route>

        <Route
          element={
            <ProtectedRoute>
              <DashboardLayout />
            </ProtectedRoute>
          }
        >
          <Route
            path="/dashboard"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION']}>
                <CoordinationDashboardPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/processos-seletivos"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION']}>
                <SelectionProcessesPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/processos-seletivos/novo"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION']}>
                <SelectionProcessFormPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/processos-seletivos/:id/editar"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION']}>
                <SelectionProcessFormPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/inscricoes"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION']}>
                <ApplicationsListPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/inscricoes/:id"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION']}>
                <CandidateDetailsPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/alunos"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION']}>
                <StudentsPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/alunos/:id"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION']}>
                <StudentDetailsPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/turmas"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION', 'TEACHER']}>
                <ClassesPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/turmas/:id"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION', 'TEACHER']}>
                <ClassDetailsPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/professores"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION']}>
                <TeachersPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/frequencia"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION', 'TEACHER']}>
                <AttendancePage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/area-aluno"
            element={
              <ProtectedRoute requiredRoles={['STUDENT']}>
                <StudentAreaPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/materiais"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION', 'TEACHER', 'STUDENT']}>
                <MaterialsPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/comunicados"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION', 'TEACHER', 'STUDENT']}>
                <AnnouncementsPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/relatorios"
            element={
              <ProtectedRoute requiredRoles={['ADMIN', 'COORDINATION']}>
                <ReportsPage />
              </ProtectedRoute>
            }
          />

          <Route path="/acesso-negado" element={<AccessDeniedPage />} />
        </Route>

        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </Suspense>
  );
}
