package br.com.challenge.employee_checkin.repositories;

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
            SELECT w.*
            FROM work_records w
            INNER JOIN employees e ON w.employee_id = e.id
            WHERE LOWER(e.name) LIKE LOWER('%' || COALESCE(:name, '') || '%')
              AND w.checkin_time >= COALESCE(:startDate, '1970-01-01'::timestamp)
              AND w.checkin_time <= COALESCE(:endDate, '9999-12-31'::timestamp)
            ORDER BY w.checkin_time ASC
        """,
            countQuery = """
            SELECT COUNT(*)
            FROM work_records w
            INNER JOIN employees e ON w.employee_id = e.id
            WHERE LOWER(e.name) LIKE LOWER('%' || COALESCE(:name, '') || '%')
              AND w.checkin_time >= COALESCE(:startDate, '1970-01-01'::timestamp)
              AND w.checkin_time <= COALESCE(:endDate, '9999-12-31'::timestamp)
        """,
            nativeQuery = true
    )
    Page<WorkRecords> getWorkRecords(
            @Param("name") String name,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
