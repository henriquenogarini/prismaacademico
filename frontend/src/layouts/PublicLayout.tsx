import { Outlet } from 'react-router-dom';
import PublicHeader from '../components/layout/PublicHeader';

export default function PublicLayout() {
  return (
    <div className="min-h-screen bg-white">
      <PublicHeader />
      <main>
        <Outlet />
      </main>
    </div>
  );
}
