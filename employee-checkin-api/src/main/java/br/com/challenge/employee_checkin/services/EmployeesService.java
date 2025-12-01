package br.com.challenge.employee_checkin.services;

import br.com.challenge.employee_checkin.ResourceNotFoundException;
import br.com.challenge.employee_checkin.dto.LoginRequest;
import br.com.challenge.employee_checkin.dto.LoginResponse;
import br.com.challenge.employee_checkin.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Logger logger = Logger.getLogger(EmployeeService.class.getName());

    public LoginResponse login(LoginRequest loginRequest) {
        var employee = employeeRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new ResourceNotFoundException("Email or password invalid."));

        if (!"123".equals(loginRequest.password())) {
            throw new ResourceNotFoundException("Email or password invalid.");
        }

        return new LoginResponse(employee.getId(), employee.getName(), employee.getEmail(), employee.getRole().name());
    }
}
