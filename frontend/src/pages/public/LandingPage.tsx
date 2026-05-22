import { Link } from 'react-router-dom';
import { Triangle, Users, BookOpen, BarChart2, ArrowRight, Heart, Code2, GraduationCap } from 'lucide-react';

const stats = [
  { value: '200+', label: 'Alunos atendidos por semestre' },
  { value: '20+', label: 'Professores voluntários' },
  { value: '8', label: 'Disciplinas ofertadas' },
  { value: '95%', label: 'Taxa de aprovação ENEM' },
];

const features = [
  {
    icon: Users,
    title: 'Gestão de Inscrições',
    description: 'Processo seletivo digital com acompanhamento de status em tempo real para candidatos.',
  },
  {
    icon: BookOpen,
    title: 'Controle de Frequência',
    description: 'Registro digital de presença por turma e disciplina, com relatórios automáticos.',
  },
  {
    icon: BarChart2,
    title: 'Relatórios Completos',
    description: 'Dashboard com indicadores de desempenho, frequência e evolução da turma.',
  },
  {
    icon: GraduationCap,
    title: 'Área do Aluno',
    description: 'Acesso a materiais, comunicados, frequência e dados académicos pelo aluno.',
  },
];

export default function LandingPage() {
  return (
    <div>
      <section className="bg-gradient-hero text-white py-24 px-4">
        <div className="max-w-4xl mx-auto text-center">
          <div className="inline-flex items-center gap-2 px-3 py-1.5 rounded-full bg-white/10 border border-white/20 text-xs font-medium text-white/80 mb-6">
            <Heart className="w-3.5 h-3.5 fill-current" />
            Projeto de extensão — UTFPR-CP
          </div>
          <h1 className="text-4xl sm:text-5xl font-extrabold leading-tight mb-4">
            Gestão acadêmica para o{' '}
            <span className="text-prismaCyan-300">Cursinho Prisma</span>
          </h1>
          <p className="text-lg text-white/80 max-w-2xl mx-auto mb-8">
            Plataforma integrada para gerenciar inscrições, frequência, turmas e comunicação —
            desenvolvida por e para a comunidade da UTFPR-CP.
          </p>
          <div className="flex flex-col sm:flex-row gap-3 justify-center">
            <Link
              to="/inscricao"
              className="inline-flex items-center gap-2 px-6 py-3 rounded-xl bg-white text-prismaBlue-700 font-semibold hover:bg-prismaLight transition-colors shadow-lg"
            >
              Fazer inscrição
              <ArrowRight className="w-4 h-4" />
            </Link>
            <Link
              to="/login"
              className="inline-flex items-center gap-2 px-6 py-3 rounded-xl border border-white/30 bg-white/10 text-white font-medium hover:bg-white/20 transition-colors"
            >
              Acessar sistema
            </Link>
          </div>
        </div>
      </section>

      <section className="bg-white border-b border-prismaGray-100 py-12 px-4">
        <div className="max-w-4xl mx-auto grid grid-cols-2 sm:grid-cols-4 gap-8 text-center">
          {stats.map((s) => (
            <div key={s.label}>
              <p className="text-3xl font-extrabold text-prismaBlue-700">{s.value}</p>
              <p className="text-sm text-prismaGray-500 mt-1">{s.label}</p>
            </div>
          ))}
        </div>
      </section>

      <section className="py-20 px-4 bg-prismaLight">
        <div className="max-w-3xl mx-auto text-center">
          <div className="inline-flex items-center justify-center w-12 h-12 rounded-2xl bg-gradient-to-br from-prismaBlue-600 to-prismaCyan-600 mb-4 mx-auto">
            <Triangle className="w-6 h-6 text-white fill-white" />
          </div>
          <h2 className="text-3xl font-bold text-prismaDark-900 mb-4">Sobre o Cursinho Prisma</h2>
          <p className="text-prismaGray-600 leading-relaxed mb-4">
            O Cursinho Comunitário Prisma é um projeto de extensão da{' '}
            <strong>Universidade Tecnológica Federal do Paraná — Campus Cornélio Procópio (UTFPR-CP)</strong>.
            Oferece preparação gratuita para o ENEM a estudantes de baixa renda da região,
            contando com professores voluntários — alunos e docentes da UTFPR.
          </p>
          <p className="text-prismaGray-600 leading-relaxed">
            O <strong>Prisma Acadêmico</strong> é a ferramenta digital que apoia a gestão desse projeto,
            centralizando inscrições, controle de presença, comunicação e relatórios em uma única plataforma.
          </p>
        </div>
      </section>

      <section className="py-20 px-4 bg-white">
        <div className="max-w-5xl mx-auto">
          <h2 className="text-3xl font-bold text-center text-prismaDark-900 mb-12">
            Funcionalidades principais
          </h2>
          <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {features.map((f) => {
              const Icon = f.icon;
              return (
                <div key={f.title} className="card card-hover p-6">
                  <div className="w-10 h-10 rounded-xl bg-prismaBlue-50 flex items-center justify-center mb-4">
                    <Icon className="w-5 h-5 text-prismaBlue-700" />
                  </div>
                  <h3 className="font-semibold text-prismaDark-800 mb-2">{f.title}</h3>
                  <p className="text-sm text-prismaGray-500">{f.description}</p>
                </div>
              );
            })}
          </div>
        </div>
      </section>

      <section className="py-16 px-4 bg-prismaDark-900 text-white">
        <div className="max-w-3xl mx-auto text-center">
          <Code2 className="w-10 h-10 text-prismaCyan-400 mx-auto mb-4" />
          <h2 className="text-2xl font-bold mb-3">Software livre, para a comunidade</h2>
          <p className="text-white/70 mb-6">
            O Prisma Acadêmico é desenvolvido como projeto de extensão universitária e
            disponibilizado sob licença MIT. Qualquer cursinho comunitário pode utilizar,
            adaptar e melhorar o sistema.
          </p>
          <Link
            to="/login"
            className="inline-flex items-center gap-2 px-6 py-3 rounded-xl bg-prismaCyan-600 text-white font-semibold hover:bg-prismaCyan-700 transition-colors"
          >
            Acessar o sistema
            <ArrowRight className="w-4 h-4" />
          </Link>
        </div>
      </section>

      <footer className="bg-prismaDark-950 text-white/50 py-8 px-4 text-center text-xs">
        <p>Prisma Acadêmico · Cursinho Comunitário Prisma · UTFPR-CP · Licença MIT</p>
      </footer>
    </div>
  );
}
