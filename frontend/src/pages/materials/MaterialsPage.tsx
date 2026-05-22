import { useEffect, useState } from 'react';
import { FileText, Video, Image, Layers, HelpCircle, ExternalLink } from 'lucide-react';
import PageHeader from '../../components/common/PageHeader';
import Card from '../../components/common/Card';
import Loading from '../../components/common/Loading';
import { materialService } from '../../services/materialService';
import type { Material, MaterialType } from '../../types/material';
import { formatDate } from '../../utils/formatDate';

const typeIcon: Record<MaterialType, React.ComponentType<{ className?: string }>> = {
  PDF: FileText,
  VIDEO: Video,
  SLIDE: Layers,
  EXERCISE: HelpCircle,
  OTHER: ExternalLink,
};

const typeLabel: Record<MaterialType, string> = {
  PDF: 'PDF',
  VIDEO: 'Vídeo',
  SLIDE: 'Slide',
  EXERCISE: 'Exercício',
  OTHER: 'Outro',
};

export default function MaterialsPage() {
  const [materials, setMaterials] = useState<Material[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    materialService.list().then(setMaterials).finally(() => setLoading(false));
  }, []);

  if (loading) return <Loading fullPage />;

  const bySubject: Record<string, Material[]> = {};
  materials.forEach((m) => {
    if (!bySubject[m.subject]) bySubject[m.subject] = [];
    bySubject[m.subject].push(m);
  });

  return (
    <div>
      <PageHeader title="Materiais" subtitle="Recursos de estudo por disciplina" />

      {Object.entries(bySubject).map(([subject, mats]) => (
        <div key={subject} className="mb-6">
          <h2 className="text-sm font-semibold text-prismaGray-500 uppercase tracking-wide mb-3">{subject}</h2>
          <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-3">
            {mats.map((m) => {
              const Icon = typeIcon[m.type];
              return (
                <Card key={m.id} hover>
                  <div className="p-4 flex gap-3">
                    <div className="w-10 h-10 rounded-xl bg-prismaBlue-50 flex items-center justify-center flex-shrink-0">
                      <Icon className="w-5 h-5 text-prismaBlue-700" />
                    </div>
                    <div className="min-w-0">
                      <p className="text-sm font-medium text-prismaDark-800 truncate">{m.title}</p>
                      <p className="text-xs text-prismaGray-500">{typeLabel[m.type]} · {formatDate(m.createdAt)}</p>
                      {m.fileUrl && (
                        <a
                          href={m.fileUrl}
                          target="_blank"
                          rel="noopener noreferrer"
                          className="text-xs text-prismaBlue-700 hover:underline mt-1 inline-flex items-center gap-1"
                        >
                          Acessar <ExternalLink className="w-3 h-3" />
                        </a>
                      )}
                    </div>
                  </div>
                </Card>
              );
            })}
          </div>
        </div>
      ))}
    </div>
  );
}
