package br.com.challenge.employee_checkin.dtos;

public record LoginResponse(Long id, String name, String email, String role, WorkRecord lastWorkRecord, String token) {}
