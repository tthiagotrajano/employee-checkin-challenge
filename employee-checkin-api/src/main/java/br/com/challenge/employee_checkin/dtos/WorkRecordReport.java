package br.com.challenge.employee_checkin.dtos;

import java.time.LocalDateTime;

public record WorkRecordReport (Long id, LocalDateTime checkInTime, LocalDateTime checkOutTime, Long duration, Long employeeId, String name) {}
