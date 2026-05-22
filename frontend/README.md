# Prisma Acadêmico — Frontend

Sistema de gestão acadêmica para o **Cursinho Comunitário Prisma** da UTFPR-CP.

## Tecnologias

| Tecnologia | Versão |
|---|---|
| React | 18.2.0 |
| TypeScript | 5.3.3 |
| Vite | 5.1.4 |
| Tailwind CSS | 3.4.1 |
| React Router DOM | 6.22.3 |
| Axios | 1.6.7 |
| React Hook Form | 7.50.1 |
| Zod | 3.22.4 |
| Recharts | 2.12.1 |
| Lucide React | 0.330.0 |

## Como executar

```bash
npm install && npm run dev
```

O servidor de desenvolvimento estará disponível em `http://localhost:3000`.

## Usuários de demonstração

| E-mail | Senha | Perfil |
|---|---|---|
| admin@prisma.com | 123456 | Administrador |
| coordination@prisma.com | 123456 | Coordenação |
| teacher@prisma.com | 123456 | Professor |
| student@prisma.com | 123456 | Aluno |
| candidate@prisma.com | 123456 | Candidato |

Na tela de login, utilize os botões de acesso rápido para entrar com cada perfil sem precisar digitar credenciais.

## Modo mock

Por padrão, a aplicação utiliza dados simulados (sem necessidade de backend).  
Isso é controlado pela variável de ambiente:

```env
VITE_USE_MOCKS=true
```

Para conectar ao backend real, defina `VITE_USE_MOCKS=false` e configure `VITE_API_URL` no arquivo `.env`.

## Páginas implementadas

- **Pública**: Landing Page, Formulário de Inscrição, Status da Inscrição
- **Autenticação**: Login
- **Coordenação/Admin**: Dashboard, Processos Seletivos, Inscrições, Alunos, Turmas, Professores, Frequência, Materiais, Comunicados, Relatórios
- **Aluno**: Área do Aluno

## Observações

- Coloque o logotipo em `src/assets/logo-prisma.png` para que apareça nos cabeçalhos.
- O build de produção é gerado em `dist/` com `npm run build`.

## Licença

MIT
