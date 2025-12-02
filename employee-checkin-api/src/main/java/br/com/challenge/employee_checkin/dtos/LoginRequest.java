package br.com.challenge.employee_checkin.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull @NotBlank @Email String email,
        @NotNull @NotBlank String password
) {}
