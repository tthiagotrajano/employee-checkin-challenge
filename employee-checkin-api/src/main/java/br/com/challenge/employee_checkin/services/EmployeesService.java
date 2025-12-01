package br.com.challenge.employee_checkin.services;

import br.com.challenge.employee_checkin.exceptions.ConflictException;
import br.com.challenge.employee_checkin.exceptions.NotFoundException;
import br.com.challenge.employee_checkin.dto.LoginRequest;
import br.com.challenge.employee_checkin.dto.LoginResponse;
import br.com.challenge.employee_checkin.repositories.EmployeesRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EmployeesService {

    @Autowired
    private EmployeesRepository employeesRepository;

    private Logger logger = Logger.getLogger(EmployeesService.class.getName());

    public LoginResponse login(LoginRequest loginRequest, HttpSession session) {
        var employee = employeesRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new NotFoundException("Email or password invalid."));

        if (!"123".equals(loginRequest.password())) {
            throw new NotFoundException("Email or password invalid.");
        }

        session.setAttribute("employeeId", employee.getId());

        return new LoginResponse(employee.getId(), employee.getName(), employee.getEmail(), employee.getRole().name());
    }

    public void logout(HttpSession session) {
        Long employeeId = (Long) session.getAttribute("employeeId");

        if (employeeId == null) {
            throw new ConflictException("You are not logged in. Please log in.");
        }

        session.setAttribute("employeeId", null);
    }
}
