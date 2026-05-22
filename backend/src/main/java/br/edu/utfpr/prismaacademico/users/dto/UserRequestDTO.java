package br.edu.utfpr.prismaacademico.users.dto;

import br.edu.utfpr.prismaacademico.users.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 150)
        String name,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, max = 100)
        String password,

        @NotNull(message = "Papel do usuário é obrigatório")
        UserRole role
) {}
