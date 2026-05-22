package br.edu.utfpr.prismaacademico.attendance.repository;

import br.edu.utfpr.prismaacademico.attendance.entity.Attendance;
import br.edu.utfpr.prismaacademico.attendance.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    List<Attendance> findAllByOrderByCreatedAtDesc();

    List<Attendance> findByLessonIdOrderByStudentCandidateFullNameAsc(UUID lessonId);

    List<Attendance> findByStudentIdOrderByLessonLessonDateDesc(UUID studentId);

    boolean existsByLessonIdAndStudentId(UUID lessonId, UUID studentId);

    long countByStudentId(UUID studentId);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.status = br.edu.utfpr.prismaacademico.attendance.enums.AttendanceStatus.PRESENT")
    long countByStudentIdAndStatusPresent(@Param("studentId") UUID studentId);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.lesson.classGroup.id = :classGroupId")
    long countByClassGroupId(@Param("classGroupId") UUID classGroupId);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.lesson.classGroup.id = :classGroupId AND a.status = 'PRESENT'")
    long countPresentByClassGroupId(@Param("classGroupId") UUID classGroupId);

    @Query("SELECT a.lesson.classGroup.name, " +
           "(SUM(CASE WHEN a.status = br.edu.utfpr.prismaacademico.attendance.enums.AttendanceStatus.PRESENT THEN 1.0 ELSE 0.0 END) / COUNT(a)) * 100 " +
           "FROM Attendance a GROUP BY a.lesson.classGroup.id, a.lesson.classGroup.name")
    List<Object[]> attendancePercentageByClass();
}

