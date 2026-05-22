package br.edu.utfpr.prismaacademico;

import br.edu.utfpr.prismaacademico.announcements.entity.Announcement;
import br.edu.utfpr.prismaacademico.announcements.repository.AnnouncementRepository;
import br.edu.utfpr.prismaacademico.attendance.entity.Attendance;
import br.edu.utfpr.prismaacademico.attendance.enums.AttendanceStatus;
import br.edu.utfpr.prismaacademico.attendance.repository.AttendanceRepository;
import br.edu.utfpr.prismaacademico.candidates.entity.Candidate;
import br.edu.utfpr.prismaacademico.candidates.enums.CandidateStatus;
import br.edu.utfpr.prismaacademico.candidates.repository.CandidateRepository;
import br.edu.utfpr.prismaacademico.classgroups.entity.ClassGroup;
import br.edu.utfpr.prismaacademico.classgroups.enums.ClassStatus;
import br.edu.utfpr.prismaacademico.classgroups.repository.ClassGroupRepository;
import br.edu.utfpr.prismaacademico.lessons.entity.Lesson;
import br.edu.utfpr.prismaacademico.lessons.repository.LessonRepository;
import br.edu.utfpr.prismaacademico.materials.entity.Material;
import br.edu.utfpr.prismaacademico.materials.repository.MaterialRepository;
import br.edu.utfpr.prismaacademico.selectionprocess.entity.SelectionProcess;
import br.edu.utfpr.prismaacademico.selectionprocess.enums.SelectionProcessStatus;
import br.edu.utfpr.prismaacademico.selectionprocess.repository.SelectionProcessRepository;
import br.edu.utfpr.prismaacademico.students.entity.Student;
import br.edu.utfpr.prismaacademico.students.entity.StudentClass;
import br.edu.utfpr.prismaacademico.students.enums.StudentStatus;
import br.edu.utfpr.prismaacademico.students.repository.StudentClassRepository;
import br.edu.utfpr.prismaacademico.students.repository.StudentRepository;
import br.edu.utfpr.prismaacademico.subjects.entity.Subject;
import br.edu.utfpr.prismaacademico.subjects.repository.SubjectRepository;
import br.edu.utfpr.prismaacademico.teachers.entity.Teacher;
import br.edu.utfpr.prismaacademico.teachers.repository.TeacherRepository;
import br.edu.utfpr.prismaacademico.users.entity.User;
import br.edu.utfpr.prismaacademico.users.enums.UserRole;
import br.edu.utfpr.prismaacademico.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final SelectionProcessRepository selectionProcessRepository;
    private final CandidateRepository candidateRepository;
    private final StudentRepository studentRepository;
    private final StudentClassRepository studentClassRepository;
    private final ClassGroupRepository classGroupRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;
    private final AttendanceRepository attendanceRepository;
    private final MaterialRepository materialRepository;
    private final AnnouncementRepository announcementRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            log.info("Dados de demonstração já existem. Seed ignorado.");
            return;
        }

        log.info("Populando dados de demonstração...");

        String encodedPassword = passwordEncoder.encode("123456");

        // ============ USERS ============
        User admin = userRepository.save(User.builder()
                .name("Administrador Prisma").email("admin@prisma.com")
                .passwordHash(encodedPassword).role(UserRole.ADMIN).active(true).build());

        User coordination = userRepository.save(User.builder()
                .name("Coordenação Prisma").email("coordination@prisma.com")
                .passwordHash(encodedPassword).role(UserRole.COORDINATION).active(true).build());

        User teacherUser = userRepository.save(User.builder()
                .name("Professor Demo").email("teacher@prisma.com")
                .passwordHash(encodedPassword).role(UserRole.TEACHER).active(true).build());

        User studentUser = userRepository.save(User.builder()
                .name("Aluno Demo").email("student@prisma.com")
                .passwordHash(encodedPassword).role(UserRole.STUDENT).active(true).build());

        User candidateUser = userRepository.save(User.builder()
                .name("Candidato Demo").email("candidate@prisma.com")
                .passwordHash(encodedPassword).role(UserRole.CANDIDATE).active(true).build());

        // Extra teacher users
        User teacherUser2 = userRepository.save(User.builder()
                .name("Camila Souza").email("camila.souza@prisma.com")
                .passwordHash(encodedPassword).role(UserRole.TEACHER).active(true).build());

        User teacherUser3 = userRepository.save(User.builder()
                .name("Rafael Martins").email("rafael.martins@prisma.com")
                .passwordHash(encodedPassword).role(UserRole.TEACHER).active(true).build());

        User teacherUser4 = userRepository.save(User.builder()
                .name("Felipe Andrade").email("felipe.andrade@prisma.com")
                .passwordHash(encodedPassword).role(UserRole.TEACHER).active(true).build());

        User teacherUser5 = userRepository.save(User.builder()
                .name("Juliana Rocha").email("juliana.rocha@prisma.com")
                .passwordHash(encodedPassword).role(UserRole.TEACHER).active(true).build());

        // ============ SELECTION PROCESS ============
        SelectionProcess sp = selectionProcessRepository.save(SelectionProcess.builder()
                .title("Processo Seletivo Prisma 2026/1")
                .year(2026).semester(1)
                .startDate(LocalDate.of(2026, 1, 15))
                .endDate(LocalDate.of(2026, 2, 28))
                .vacancies(40)
                .description("Processo seletivo para ingresso no Cursinho Comunitário Prisma no primeiro semestre de 2026.")
                .status(SelectionProcessStatus.OPEN)
                .build());

        // ============ SUBJECTS ============
        Subject math = subjectRepository.save(Subject.builder().name("Matemática").area("Exatas").active(true).build());
        Subject writing = subjectRepository.save(Subject.builder().name("Redação").area("Linguagens").active(true).build());
        Subject languages = subjectRepository.save(Subject.builder().name("Linguagens").area("Linguagens").active(true).build());
        Subject humanities = subjectRepository.save(Subject.builder().name("Ciências Humanas").area("Humanas").active(true).build());
        Subject nature = subjectRepository.save(Subject.builder().name("Ciências da Natureza").area("Exatas").active(true).build());

        // ============ CLASS GROUPS ============
        ClassGroup classA = classGroupRepository.save(ClassGroup.builder()
                .name("Prisma 2026/1 - Turma A").year(2026).semester(1)
                .shift("Manhã").status(ClassStatus.ACTIVE).build());

        ClassGroup classB = classGroupRepository.save(ClassGroup.builder()
                .name("Prisma 2026/1 - Turma B").year(2026).semester(1)
                .shift("Tarde").status(ClassStatus.ACTIVE).build());

        // ============ TEACHERS ============
        Teacher t1 = teacherRepository.save(Teacher.builder().user(teacherUser2).course("Bacharelado em Matemática")
                .institution("UTFPR-CP").active(true).build());
        Teacher t2 = teacherRepository.save(Teacher.builder().user(teacherUser3).course("Letras - Português")
                .institution("UNICAMP").active(true).build());
        Teacher t3 = teacherRepository.save(Teacher.builder().user(teacherUser4).course("História")
                .institution("UEL").active(true).build());
        Teacher t4 = teacherRepository.save(Teacher.builder().user(teacherUser5).course("Biologia")
                .institution("UTFPR-CP").active(true).build());
        Teacher t5 = teacherRepository.save(Teacher.builder().user(teacherUser).course("Física")
                .institution("UTFPR-CP").active(true).build());

        // ============ CANDIDATES ============
        Candidate c1 = candidateRepository.save(Candidate.builder()
                .selectionProcess(sp).fullName("Ana Clara Santos").documentNumber("111.222.333-44")
                .birthDate(LocalDate.of(2007, 3, 15)).phone("(43)99111-1111")
                .email("ana.clara@email.com").schoolName("Colégio Estadual Castro Alves")
                .schoolYear("3º Ano EM").publicSchool(true)
                .incomePerCapita(new BigDecimal("450.00")).status(CandidateStatus.APPROVED).build());

        Candidate c2 = candidateRepository.save(Candidate.builder()
                .selectionProcess(sp).fullName("João Pedro Almeida").documentNumber("222.333.444-55")
                .birthDate(LocalDate.of(2006, 7, 22)).phone("(43)99222-2222")
                .email("joao.pedro@email.com").schoolName("Colégio Estadual Monteiro Lobato")
                .schoolYear("3º Ano EM").publicSchool(true)
                .incomePerCapita(new BigDecimal("380.00")).status(CandidateStatus.APPROVED).build());

        Candidate c3 = candidateRepository.save(Candidate.builder()
                .selectionProcess(sp).fullName("Mariana Oliveira").documentNumber("333.444.555-66")
                .birthDate(LocalDate.of(2007, 11, 5)).phone("(43)99333-3333")
                .email("mariana@email.com").schoolName("Colégio Estadual Cristo Rei")
                .schoolYear("3º Ano EM").publicSchool(true)
                .incomePerCapita(new BigDecimal("520.00")).status(CandidateStatus.APPROVED).build());

        Candidate c4 = candidateRepository.save(Candidate.builder()
                .selectionProcess(sp).fullName("Lucas Ferreira").documentNumber("444.555.666-77")
                .birthDate(LocalDate.of(2008, 1, 30)).phone("(43)99444-4444")
                .email("lucas.ferreira@email.com").schoolName("Escola Estadual Professora Maria do Carmo")
                .schoolYear("2º Ano EM").publicSchool(true)
                .incomePerCapita(new BigDecimal("290.00")).status(CandidateStatus.UNDER_REVIEW).build());

        Candidate c5 = candidateRepository.save(Candidate.builder()
                .selectionProcess(sp).fullName("Beatriz Lima").documentNumber("555.666.777-88")
                .birthDate(LocalDate.of(2007, 6, 18)).phone("(43)99555-5555")
                .email("beatriz.lima@email.com").schoolName("Colégio Estadual Castro Alves")
                .schoolYear("3º Ano EM").publicSchool(true)
                .incomePerCapita(new BigDecimal("410.00")).status(CandidateStatus.SUBMITTED).build());

        Candidate c6 = candidateRepository.save(Candidate.builder()
                .selectionProcess(sp).fullName("Carlos Eduardo Mendes").documentNumber("666.777.888-99")
                .birthDate(LocalDate.of(2006, 9, 12)).phone("(43)99666-6666")
                .email("carlos.mendes@email.com").schoolName("Colégio Estadual Monteiro Lobato")
                .schoolYear("3º Ano EM").publicSchool(true)
                .incomePerCapita(new BigDecimal("350.00")).status(CandidateStatus.APPROVED).build());

        Candidate c7 = candidateRepository.save(Candidate.builder()
                .selectionProcess(sp).fullName("Fernanda Costa").documentNumber("777.888.999-00")
                .birthDate(LocalDate.of(2007, 4, 25)).phone("(43)99777-7777")
                .email("fernanda.costa@email.com").schoolName("Escola Estadual Professora Maria do Carmo")
                .schoolYear("3º Ano EM").publicSchool(true)
                .incomePerCapita(new BigDecimal("470.00")).status(CandidateStatus.REJECTED)
                .observation("Renda per capita acima do limite.").build());

        // ============ STUDENTS ============
        Student s1 = studentRepository.save(Student.builder().candidate(c1)
                .registrationNumber("PRISMA-2026-0001").status(StudentStatus.ACTIVE)
                .enrollmentDate(LocalDate.of(2026, 3, 1)).build());

        Student s2 = studentRepository.save(Student.builder().candidate(c2)
                .registrationNumber("PRISMA-2026-0002").status(StudentStatus.ACTIVE)
                .enrollmentDate(LocalDate.of(2026, 3, 1)).build());

        Student s3 = studentRepository.save(Student.builder().candidate(c3)
                .registrationNumber("PRISMA-2026-0003").status(StudentStatus.ACTIVE)
                .enrollmentDate(LocalDate.of(2026, 3, 1)).build());

        Student s4 = studentRepository.save(Student.builder().candidate(c6)
                .registrationNumber("PRISMA-2026-0004").status(StudentStatus.ACTIVE)
                .enrollmentDate(LocalDate.of(2026, 3, 1)).build());

        // ============ STUDENT - CLASS ENROLLMENTS ============
        studentClassRepository.save(StudentClass.builder().student(s1).classGroup(classA).build());
        studentClassRepository.save(StudentClass.builder().student(s2).classGroup(classA).build());
        studentClassRepository.save(StudentClass.builder().student(s3).classGroup(classB).build());
        studentClassRepository.save(StudentClass.builder().student(s4).classGroup(classB).build());

        // ============ LESSONS ============
        Lesson l1 = lessonRepository.save(Lesson.builder()
                .classGroup(classA).subject(math).teacher(t1).title("Funções do 2º Grau")
                .lessonDate(LocalDate.of(2026, 4, 5))
                .startTime(LocalTime.of(8, 0)).endTime(LocalTime.of(10, 0)).build());

        Lesson l2 = lessonRepository.save(Lesson.builder()
                .classGroup(classA).subject(languages).teacher(t2).title("Interpretação de Texto - ENEM")
                .lessonDate(LocalDate.of(2026, 4, 7))
                .startTime(LocalTime.of(8, 0)).endTime(LocalTime.of(10, 0)).build());

        Lesson l3 = lessonRepository.save(Lesson.builder()
                .classGroup(classB).subject(humanities).teacher(t3).title("Brasil República - Revolução de 1930")
                .lessonDate(LocalDate.of(2026, 4, 6))
                .startTime(LocalTime.of(14, 0)).endTime(LocalTime.of(16, 0)).build());

        Lesson l4 = lessonRepository.save(Lesson.builder()
                .classGroup(classB).subject(nature).teacher(t4).title("Genética e Hereditariedade")
                .lessonDate(LocalDate.of(2026, 4, 8))
                .startTime(LocalTime.of(14, 0)).endTime(LocalTime.of(16, 0)).build());

        // ============ ATTENDANCE ============
        attendanceRepository.save(Attendance.builder().lesson(l1).student(s1).status(AttendanceStatus.PRESENT).build());
        attendanceRepository.save(Attendance.builder().lesson(l1).student(s2).status(AttendanceStatus.PRESENT).build());
        attendanceRepository.save(Attendance.builder().lesson(l2).student(s1).status(AttendanceStatus.ABSENT).build());
        attendanceRepository.save(Attendance.builder().lesson(l2).student(s2).status(AttendanceStatus.PRESENT).build());
        attendanceRepository.save(Attendance.builder().lesson(l3).student(s3).status(AttendanceStatus.PRESENT).build());
        attendanceRepository.save(Attendance.builder().lesson(l3).student(s4).status(AttendanceStatus.JUSTIFIED)
                .observation("Atestado médico").build());
        attendanceRepository.save(Attendance.builder().lesson(l4).student(s3).status(AttendanceStatus.PRESENT).build());
        attendanceRepository.save(Attendance.builder().lesson(l4).student(s4).status(AttendanceStatus.PRESENT).build());

        // ============ MATERIALS ============
        materialRepository.save(Material.builder().subject(math).teacher(t1)
                .title("Lista de Exercícios - Funções")
                .description("Lista com 20 exercícios sobre funções do 1º e 2º grau com gabarito.")
                .fileUrl("https://exemplo.com/materiais/funcoes.pdf").build());

        materialRepository.save(Material.builder().subject(languages).teacher(t2)
                .title("Coletânea de Textos ENEM 2024")
                .description("Textos selecionados das provas do ENEM 2024 para prática.")
                .fileUrl("https://exemplo.com/materiais/textos-enem.pdf").build());

        materialRepository.save(Material.builder().subject(writing).teacher(t2)
                .title("Estrutura da Dissertação Argumentativa")
                .description("Guia completo para escrever uma boa redação no ENEM.")
                .fileUrl("https://exemplo.com/materiais/redacao.pdf").build());

        materialRepository.save(Material.builder().subject(nature).teacher(t4)
                .title("Resumo de Genética")
                .description("Resumo completo dos principais conceitos de genética mendeliana e não-mendeliana.")
                .fileUrl("https://exemplo.com/materiais/genetica.pdf").build());

        // ============ ANNOUNCEMENTS ============
        announcementRepository.save(Announcement.builder().author(coordination)
                .title("Bem-vindos ao Cursinho Comunitário Prisma 2026/1!")
                .content("É com grande satisfação que damos as boas-vindas a todos os alunos aprovados no Processo Seletivo 2026/1. " +
                        "As aulas começam em 10 de março. Confiram o cronograma no portal.")
                .build());

        announcementRepository.save(Announcement.builder().author(coordination).classGroup(classA)
                .title("Calendário de Simulados - Turma A")
                .content("O primeiro simulado da Turma A será realizado no dia 20 de abril, das 8h às 12h. " +
                        "Conteúdo: Matemática, Linguagens e Ciências da Natureza.")
                .build());

        announcementRepository.save(Announcement.builder().author(coordination).classGroup(classB)
                .title("Calendário de Simulados - Turma B")
                .content("O primeiro simulado da Turma B será realizado no dia 22 de abril, das 14h às 18h. " +
                        "Conteúdo: Ciências Humanas, Redação e Ciências da Natureza.")
                .build());

        announcementRepository.save(Announcement.builder().author(admin)
                .title("Inscrições reabertas para o Processo Seletivo 2026/1")
                .content("Informamos que as inscrições para o Processo Seletivo 2026/1 estão abertas até 28 de fevereiro. " +
                        "Candidatos de escolas públicas com renda familiar de até 1,5 salário mínimo per capita são elegíveis.")
                .build());

        log.info("Dados de demonstração criados com sucesso!");
        log.info("Usuários demo:");
        log.info("  admin@prisma.com / 123456");
        log.info("  coordination@prisma.com / 123456");
        log.info("  teacher@prisma.com / 123456");
        log.info("  student@prisma.com / 123456");
        log.info("  candidate@prisma.com / 123456");
    }
}

