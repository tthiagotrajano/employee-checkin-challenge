package br.com.challenge.employee_checkin.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "work_records")
public class WorkRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "checkin_time")
    private LocalDateTime checkInTime;

    @Column(name = "checkout_time")
    private LocalDateTime checkOutTime;

    @Column(name = "duration")
    private Long duration;
}
