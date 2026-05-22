import { useNavigate } from 'react-router-dom';
import Badge from '../common/Badge';
import { formatDate } from '../../utils/formatDate';
import type { Candidate } from '../../types/candidate';

interface RecentApplicationsTableProps {
  candidates: Candidate[];
}

export default function RecentApplicationsTable({ candidates }: RecentApplicationsTableProps) {
  const navigate = useNavigate();
  const recent = candidates.slice(0, 5);

  return (
    <div>
      <div className="overflow-x-auto">
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-prismaGray-100">
              <th className="text-left px-4 py-3 text-xs font-semibold text-prismaGray-500 uppercase tracking-wider">
                Candidato
              </th>
              <th className="text-left px-4 py-3 text-xs font-semibold text-prismaGray-500 uppercase tracking-wider hidden sm:table-cell">
                Escola
              </th>
              <th className="text-left px-4 py-3 text-xs font-semibold text-prismaGray-500 uppercase tracking-wider hidden md:table-cell">
                Data
              </th>
              <th className="text-left px-4 py-3 text-xs font-semibold text-prismaGray-500 uppercase tracking-wider">
                Status
              </th>
            </tr>
          </thead>
          <tbody className="divide-y divide-prismaGray-100">
            {recent.map((c) => (
              <tr
                key={c.id}
                onClick={() => navigate(`/inscricoes/${c.id}`)}
                className="cursor-pointer hover:bg-prismaGray-50 transition-colors"
              >
                <td className="px-4 py-3">
                  <p className="font-medium text-prismaDark-800">{c.fullName}</p>
                  <p className="text-xs text-prismaGray-500 sm:hidden">{c.school}</p>
                </td>
                <td className="px-4 py-3 text-prismaDark-600 hidden sm:table-cell">{c.school}</td>
                <td className="px-4 py-3 text-prismaGray-500 text-xs hidden md:table-cell">
                  {formatDate(c.appliedAt)}
                </td>
                <td className="px-4 py-3">
                  <Badge status={c.status} />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
