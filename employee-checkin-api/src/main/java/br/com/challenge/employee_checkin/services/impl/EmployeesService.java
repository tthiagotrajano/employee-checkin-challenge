package br.com.challenge.employee_checkin.services.impl;

import br.com.challenge.employee_checkin.dtos.WorkRecord;
import br.com.challenge.employee_checkin.exceptions.ConflictException;
import br.com.challenge.employee_checkin.exceptions.NotFoundException;
import br.com.challenge.employee_checkin.dtos.LoginRequest;
import br.com.challenge.employee_checkin.dtos.LoginResponse;
import br.com.challenge.employee_checkin.mappers.WorkRecordMapper;
import br.com.challenge.employee_checkin.models.WorkRecords;
import br.com.challenge.employee_checkin.repositories.EmployeesRepository;
import br.com.challenge.employee_checkin.repositories.WorkRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EmployeesService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private WorkRepository workRepository;

    private Logger logger = Logger.getLogger(EmployeesService.class.getName());

    public LoginResponse login(LoginRequest loginRequest, HttpSession session) {
        var employee = employeesRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new NotFoundException("Email or password invalid."));

        if (!"123".equals(loginRequest.password())) {
            throw new NotFoundException("Email or password invalid.");
        }

        session.setAttribute("employeeId", employee.getId());

        WorkRecords lastWorkRecord = workRepository.findTopByEmployeeIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(employee.getId());
        WorkRecord lastWorkRecordDTO = WorkRecordMapper.toDTO(lastWorkRecord);


        return new LoginResponse(employee.getId(), employee.getName(), employee.getEmail(), employee.getRole().name(), lastWorkRecordDTO);
    }

    public void logout(HttpSession session) {
        Long employeeId = (Long) session.getAttribute("employeeId");

        if (employeeId == null) {
            throw new ConflictException("You are not logged in. Please log in.");
        }

        session.setAttribute("employeeId", null);
    }
}
