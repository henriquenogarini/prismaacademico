import { useEffect, useState } from 'react';
import { Pin } from 'lucide-react';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Loading from '../../components/common/Loading';
import { announcementService } from '../../services/announcementService';
import type { Announcement } from '../../types/announcement';
import { formatRelativeDate } from '../../utils/formatDate';
import { cn } from '../../utils/cn';

export default function AnnouncementsPage() {
  const [announcements, setAnnouncements] = useState<Announcement[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    announcementService.list().then(setAnnouncements).finally(() => setLoading(false));
  }, []);

  if (loading) return <Loading fullPage />;

  const sorted = [...announcements].sort((a, b) => (b.isPinned ? 1 : 0) - (a.isPinned ? 1 : 0));

  return (
    <div>
      <PageHeader title="Comunicados" subtitle={`${announcements.length} comunicados`} />
      <div className="space-y-3">
        {sorted.map((a) => (
          <Card key={a.id} className={cn(a.isPinned && 'border-prismaBlue-200')}>
            <div className="p-5">
              <div className="flex items-start justify-between gap-3">
                <div className="flex-1">
                  <div className="flex items-center gap-2 flex-wrap mb-1">
                    {a.isPinned && (
                      <span className="inline-flex items-center gap-1 text-xs font-medium text-prismaBlue-700 bg-prismaBlue-50 px-2 py-0.5 rounded-full">
                        <Pin className="w-3 h-3" /> Fixado
                      </span>
                    )}
                    <h2 className="font-semibold text-prismaDark-800">{a.title}</h2>
                  </div>
                  <p className="text-sm text-prismaGray-600">{a.content}</p>
                </div>
                <p className="text-xs text-prismaGray-400 flex-shrink-0">{formatRelativeDate(a.createdAt)}</p>
              </div>
            </div>
          </Card>
        ))}
      </div>
    </div>
  );
}
