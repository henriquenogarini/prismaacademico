package br.edu.utfpr.prismaacademico.announcements.repository;

import br.edu.utfpr.prismaacademico.announcements.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {
    List<Announcement> findAllByOrderByCreatedAtDesc();
    List<Announcement> findByClassGroupIdOrderByCreatedAtDesc(UUID classGroupId);
    List<Announcement> findByClassGroupIsNullOrderByCreatedAtDesc();
}

