package br.edu.utfpr.prismaacademico.users.service;

import br.edu.utfpr.prismaacademico.common.exception.BusinessException;
import br.edu.utfpr.prismaacademico.common.exception.ResourceNotFoundException;
import br.edu.utfpr.prismaacademico.users.dto.UserRequestDTO;
import br.edu.utfpr.prismaacademico.users.dto.UserResponseDTO;
import br.edu.utfpr.prismaacademico.users.entity.User;
import br.edu.utfpr.prismaacademico.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(UserResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(UUID id) {
        return userRepository.findById(id)
                .map(UserResponseDTO::from)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
    }

    @Transactional(readOnly = true)
    public User findEntityById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com email: " + email));
    }

    @Transactional
    public UserResponseDTO create(UserRequestDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("E-mail já cadastrado: " + request.email());
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email().toLowerCase().trim())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(request.role())
                .active(true)
                .build();

        return UserResponseDTO.from(userRepository.save(user));
    }

    @Transactional
    public UserResponseDTO update(UUID id, UserRequestDTO request) {
        User user = findEntityById(id);

        if (!user.getEmail().equals(request.email().toLowerCase().trim())
                && userRepository.existsByEmail(request.email())) {
            throw new BusinessException("E-mail já cadastrado: " + request.email());
        }

        user.setName(request.name());
        user.setEmail(request.email().toLowerCase().trim());
        user.setRole(request.role());

        if (request.password() != null && !request.password().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }

        return UserResponseDTO.from(userRepository.save(user));
    }

    @Transactional
    public UserResponseDTO toggleStatus(UUID id) {
        User user = findEntityById(id);
        user.setActive(!user.isActive());
        return UserResponseDTO.from(userRepository.save(user));
    }

    @Transactional
    public void delete(UUID id) {
        User user = findEntityById(id);
        userRepository.delete(user);
    }
}

