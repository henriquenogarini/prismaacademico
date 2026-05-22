import React from 'react';
import { InboxIcon } from 'lucide-react';

interface EmptyStateProps {
  title: string;
  description?: string;
  icon?: React.ReactNode;
  action?: React.ReactNode;
}

export default function EmptyState({
  title,
  description,
  icon,
  action,
}: EmptyStateProps) {
  return (
    <div className="flex flex-col items-center justify-center py-16 text-center">
      <div className="w-16 h-16 rounded-2xl bg-prismaGray-100 flex items-center justify-center mb-4 text-prismaGray-400">
        {icon ?? <InboxIcon className="w-8 h-8" />}
      </div>
      <h3 className="text-base font-semibold text-prismaDark-700 mb-1">{title}</h3>
      {description && (
        <p className="text-sm text-prismaGray-500 max-w-sm mb-4">{description}</p>
      )}
      {action && <div className="mt-2">{action}</div>}
    </div>
  );
}
