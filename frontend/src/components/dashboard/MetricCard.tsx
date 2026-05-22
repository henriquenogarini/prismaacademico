import React from 'react';
import { TrendingUp, TrendingDown } from 'lucide-react';
import { cn } from '../../utils/cn';

interface MetricCardProps {
  title: string;
  value: string | number;
  subtitle?: string;
  icon: React.ReactNode;
  trend?: {
    value: number;
    label: string;
    direction: 'up' | 'down' | 'neutral';
  };
  color?: 'blue' | 'cyan' | 'indigo' | 'purple' | 'green' | 'amber';
}

const colorConfig = {
  blue: {
    bg: 'bg-prismaBlue-50',
    iconBg: 'bg-prismaBlue-100',
    iconColor: 'text-prismaBlue-700',
    valueColor: 'text-prismaBlue-900',
  },
  cyan: {
    bg: 'bg-cyan-50',
    iconBg: 'bg-cyan-100',
    iconColor: 'text-prismaCyan-700',
    valueColor: 'text-prismaCyan-900',
  },
  indigo: {
    bg: 'bg-indigo-50',
    iconBg: 'bg-indigo-100',
    iconColor: 'text-prismaIndigo-700',
    valueColor: 'text-prismaIndigo-900',
  },
  purple: {
    bg: 'bg-purple-50',
    iconBg: 'bg-purple-100',
    iconColor: 'text-prismaPurple-700',
    valueColor: 'text-prismaPurple-900',
  },
  green: {
    bg: 'bg-green-50',
    iconBg: 'bg-green-100',
    iconColor: 'text-green-700',
    valueColor: 'text-green-900',
  },
  amber: {
    bg: 'bg-amber-50',
    iconBg: 'bg-amber-100',
    iconColor: 'text-amber-700',
    valueColor: 'text-amber-900',
  },
};

export default function MetricCard({
  title,
  value,
  subtitle,
  icon,
  trend,
  color = 'blue',
}: MetricCardProps) {
  const config = colorConfig[color];

  return (
    <div className={cn('rounded-2xl p-5 border border-white/50', config.bg, 'shadow-prisma')}>
      <div className="flex items-start justify-between">
        <div>
          <p className="text-sm font-medium text-prismaGray-600">{title}</p>
          <p className={cn('text-3xl font-bold mt-1', config.valueColor)}>{value}</p>
          {subtitle && (
            <p className="text-xs text-prismaGray-500 mt-0.5">{subtitle}</p>
          )}
        </div>
        <div className={cn('w-11 h-11 rounded-xl flex items-center justify-center flex-shrink-0', config.iconBg)}>
          <span className={config.iconColor}>{icon}</span>
        </div>
      </div>
      {trend && (
        <div className="mt-3 flex items-center gap-1.5">
          {trend.direction === 'up' && (
            <TrendingUp className="w-3.5 h-3.5 text-green-600" />
          )}
          {trend.direction === 'down' && (
            <TrendingDown className="w-3.5 h-3.5 text-red-500" />
          )}
          <span
            className={cn(
              'text-xs font-medium',
              trend.direction === 'up'
                ? 'text-green-700'
                : trend.direction === 'down'
                ? 'text-red-600'
                : 'text-prismaGray-600'
            )}
          >
            {trend.value > 0 ? '+' : ''}
            {trend.value}%
          </span>
          <span className="text-xs text-prismaGray-500">{trend.label}</span>
        </div>
      )}
    </div>
  );
}
