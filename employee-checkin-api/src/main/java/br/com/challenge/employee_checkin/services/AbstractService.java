package br.com.challenge.employee_checkin.services;

import br.com.challenge.employee_checkin.exceptions.UnauthorizedException;
import br.com.challenge.employee_checkin.models.Employees;
import br.com.challenge.employee_checkin.repositories.EmployeesRepository;
import br.com.challenge.employee_checkin.security.JwtUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class AbstractService {

    @Autowired
    private HttpSession session;

    @Autowired
    private EmployeesRepository employeesRepository;

    protected Employees getEmployee(String token) {
        Long employeeId = JwtUtils.getEmployeeId(token);
        Optional<Employees> employee = employeesRepository.findById(employeeId);
        return employee.orElseThrow(() -> new UnauthorizedException("Access denied. Please login."));
    }

    protected Long getEmployeeId(String token) {
        Employees employee = getEmployee(token);
        return (employee != null) ? employee.getId() : null;
    }

    protected String getEmployeeRole(String token) {
        Employees employee = getEmployee(token);
        return (employee != null) ? employee.getRole().name() : null;
    }
}
