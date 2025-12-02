package br.com.challenge.employee_checkin.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class WorkRecord {
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
}
