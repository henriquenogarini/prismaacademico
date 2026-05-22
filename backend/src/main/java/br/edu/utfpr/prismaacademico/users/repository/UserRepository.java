package br.edu.utfpr.prismaacademico.users.repository;

import br.edu.utfpr.prismaacademico.users.entity.User;
import br.edu.utfpr.prismaacademico.users.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndActiveTrue(String email);

    boolean existsByEmail(String email);

    List<User> findAllByOrderByCreatedAtDesc();

    List<User> findByRoleOrderByNameAsc(UserRole role);

    long countByRole(UserRole role);

    long countByActive(boolean active);
}

