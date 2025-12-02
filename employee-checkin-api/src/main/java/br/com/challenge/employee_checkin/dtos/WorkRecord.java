package br.com.challenge.employee_checkin.dtos;

import java.time.LocalDateTime;

public record  WorkRecord(LocalDateTime checkInTime, LocalDateTime checkOutTime) {}
