package br.edu.utfpr.prismaacademico.candidates.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CandidateRequestDTO(
        @NotNull(message = "Processo seletivo é obrigatório")
        UUID selectionProcessId,

        @NotBlank(message = "Nome completo é obrigatório")
        @Size(min = 3, max = 150)
        String fullName,

        @NotBlank(message = "Número do documento é obrigatório")
        @Size(max = 20)
        String documentNumber,

        @Past(message = "Data de nascimento deve ser no passado")
        LocalDate birthDate,

        @Size(max = 20)
        String phone,

        @Email(message = "E-mail inválido")
        String email,

        @Size(max = 200)
        String schoolName,

        @Size(max = 50)
        String schoolYear,

        Boolean publicSchool,

        @DecimalMin(value = "0.0", message = "Renda per capita não pode ser negativa")
        BigDecimal incomePerCapita,

        String observation
) {}
