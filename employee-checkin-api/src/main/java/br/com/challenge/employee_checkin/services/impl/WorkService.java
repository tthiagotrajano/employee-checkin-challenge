package br.com.challenge.employee_checkin.services.impl;

import br.com.challenge.employee_checkin.annotations.ValidateRole;
import br.com.challenge.employee_checkin.dtos.CheckResponse;
import br.com.challenge.employee_checkin.enums.RoleEnum;
import br.com.challenge.employee_checkin.exceptions.ConflictException;
import br.com.challenge.employee_checkin.exceptions.UnauthorizedException;
import br.com.challenge.employee_checkin.models.WorkRecords;
import br.com.challenge.employee_checkin.repositories.WorkRepository;
import br.com.challenge.employee_checkin.dtos.WorkRecordReport;
import br.com.challenge.employee_checkin.security.JwtUtils;
import br.com.challenge.employee_checkin.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class WorkService extends AbstractService {

    @Autowired
    private WorkRepository workRepository;

    @ValidateRole({RoleEnum.USER, RoleEnum.ADMIN})
    public CheckResponse checkIn(String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");

        if (!JwtUtils.validateToken(token)) {
            throw new UnauthorizedException("You are not logged in. Please log in.");
        }

        Long employeeId = JwtUtils.getEmployeeId(token);

        WorkRecords lastWorkRecord = workRepository.findTopByEmployeeIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(employeeId);

        if (lastWorkRecord != null && lastWorkRecord.getCheckInTime() != null) {
            throw new ConflictException("Check-in already exists. Complete the check-out first.");
        }

        WorkRecords workRecord = new WorkRecords();
        workRecord.setEmployeeId(employeeId);
        workRecord.setCheckInTime(LocalDateTime.now());

        workRepository.save(workRecord);

        return new CheckResponse(true);

    }

    @ValidateRole({RoleEnum.USER, RoleEnum.ADMIN})
    public CheckResponse checkOut(String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");

        if (!JwtUtils.validateToken(token)) {
            throw new UnauthorizedException("You are not logged in. Please log in.");
        }

        Long employeeId = JwtUtils.getEmployeeId(token);

        WorkRecords lastWorkRecord = workRepository.findTopByEmployeeIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(employeeId);

        if (lastWorkRecord == null || lastWorkRecord.getCheckInTime() == null) {
            throw new ConflictException("Check-in not exists. Complete the check-in first.");
        }

        if (lastWorkRecord.getCheckOutTime() != null) {
            throw new ConflictException("Check-out already exists. Please perform a new check-in.");
        }

        Long duration = Duration.between(lastWorkRecord.getCheckInTime(), LocalDateTime.now()).toSeconds();
        lastWorkRecord.setCheckOutTime(LocalDateTime.now());
        lastWorkRecord.setDuration(duration);

        workRepository.save(lastWorkRecord);

        return new CheckResponse(true);
    }

    @ValidateRole({RoleEnum.ADMIN, RoleEnum.USER})
    public Page<WorkRecordReport> getWorkRecords(String name, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable, String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        return workRepository.getWorkRecords(name, startDate, endDate, getEmployeeRole(token), getEmployeeId(token), pageable);
    }
}
