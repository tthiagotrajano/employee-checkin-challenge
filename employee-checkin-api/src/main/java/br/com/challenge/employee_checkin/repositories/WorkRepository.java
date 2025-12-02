package br.com.challenge.employee_checkin.repositories;

import br.com.challenge.employee_checkin.dtos.WorkRecordReport;
import br.com.challenge.employee_checkin.models.WorkRecords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface WorkRepository extends JpaRepository<WorkRecords, Long> {

    WorkRecords findTopByEmployeeIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(Long EmployeeId);

    @Query(
            value = """
        SELECT w.id, w.checkin_time, w.checkout_time, w.duration, w.employee_id, e.name
        FROM work_records w
        INNER JOIN employees e ON w.employee_id = e.id
        WHERE (:role = 'ADMIN' OR w.employee_id = :employeeId)
          AND LOWER(e.name) LIKE LOWER('%' || COALESCE(:name, '') || '%')
          AND w.checkin_time >= COALESCE(:startDate, '1970-01-01'::timestamp)
          AND w.checkin_time <= COALESCE(:endDate, '9999-12-31'::timestamp)
        ORDER BY w.checkin_time DESC
    """,
            countQuery = """
        SELECT COUNT(*)
        FROM work_records w
        INNER JOIN employees e ON w.employee_id = e.id
        WHERE (:role = 'ADMIN' OR w.employee_id = :employeeId)
          AND LOWER(e.name) LIKE LOWER('%' || COALESCE(:name, '') || '%')
          AND w.checkin_time >= COALESCE(:startDate, '1970-01-01'::timestamp)
          AND w.checkin_time <= COALESCE(:endDate, '9999-12-31'::timestamp)
    """,
            nativeQuery = true
    )
    Page<WorkRecordReport> getWorkRecords(
            @Param("name") String name,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("role") String role,
            @Param("employeeId") Long employeeId,
            Pageable pageable
    );

}
