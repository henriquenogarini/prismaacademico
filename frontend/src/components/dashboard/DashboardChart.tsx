import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  AreaChart,
  Area,
  Legend,
} from 'recharts';

interface BarChartData {
  name: string;
  value: number;
  color?: string;
}

interface LineChartData {
  month: string;
  enrolled: number;
  approved: number;
}

interface DashboardBarChartProps {
  data: BarChartData[];
  title?: string;
}

interface DashboardAreaChartProps {
  data: LineChartData[];
  title?: string;
}

export function DashboardBarChart({ data }: DashboardBarChartProps) {
  return (
    <ResponsiveContainer width="100%" height={220}>
      <BarChart data={data} margin={{ top: 5, right: 10, left: -10, bottom: 5 }}>
        <CartesianGrid strokeDasharray="3 3" stroke="#e2e8f0" />
        <XAxis
          dataKey="name"
          tick={{ fontSize: 11, fill: '#64748b' }}
          axisLine={false}
          tickLine={false}
        />
        <YAxis
          tick={{ fontSize: 11, fill: '#64748b' }}
          axisLine={false}
          tickLine={false}
        />
        <Tooltip
          contentStyle={{
            borderRadius: '0.75rem',
            border: '1px solid #e2e8f0',
            fontSize: 12,
            boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)',
          }}
          cursor={{ fill: 'rgba(30, 64, 175, 0.05)' }}
        />
        <Bar dataKey="value" radius={[6, 6, 0, 0]} fill="#1d4ed8" />
      </BarChart>
    </ResponsiveContainer>
  );
}

export function DashboardAreaChart({ data }: DashboardAreaChartProps) {
  return (
    <ResponsiveContainer width="100%" height={220}>
      <AreaChart data={data} margin={{ top: 5, right: 10, left: -10, bottom: 5 }}>
        <defs>
          <linearGradient id="colorEnrolled" x1="0" y1="0" x2="0" y2="1">
            <stop offset="5%" stopColor="#1d4ed8" stopOpacity={0.2} />
            <stop offset="95%" stopColor="#1d4ed8" stopOpacity={0} />
          </linearGradient>
          <linearGradient id="colorApproved" x1="0" y1="0" x2="0" y2="1">
            <stop offset="5%" stopColor="#0891b2" stopOpacity={0.2} />
            <stop offset="95%" stopColor="#0891b2" stopOpacity={0} />
          </linearGradient>
        </defs>
        <CartesianGrid strokeDasharray="3 3" stroke="#e2e8f0" />
        <XAxis
          dataKey="month"
          tick={{ fontSize: 11, fill: '#64748b' }}
          axisLine={false}
          tickLine={false}
        />
        <YAxis
          tick={{ fontSize: 11, fill: '#64748b' }}
          axisLine={false}
          tickLine={false}
        />
        <Tooltip
          contentStyle={{
            borderRadius: '0.75rem',
            border: '1px solid #e2e8f0',
            fontSize: 12,
            boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)',
          }}
        />
        <Legend
          wrapperStyle={{ fontSize: 12, color: '#64748b' }}
          formatter={(value) => (value === 'enrolled' ? 'Matriculados' : 'Aprovados')}
        />
        <Area
          type="monotone"
          dataKey="enrolled"
          stroke="#1d4ed8"
          strokeWidth={2}
          fill="url(#colorEnrolled)"
        />
        <Area
          type="monotone"
          dataKey="approved"
          stroke="#0891b2"
          strokeWidth={2}
          fill="url(#colorApproved)"
        />
      </AreaChart>
    </ResponsiveContainer>
  );
}
