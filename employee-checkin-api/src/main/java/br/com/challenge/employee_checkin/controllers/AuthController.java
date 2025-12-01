package br.com.challenge.employee_checkin.controllers;

import br.com.challenge.employee_checkin.dto.LoginRequest;
import br.com.challenge.employee_checkin.dto.LoginResponse;
import br.com.challenge.employee_checkin.services.EmployeesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private EmployeesService employeesService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        LoginResponse loginResponse = employeesService.login(loginRequest, session);
        return loginResponse;
    }

    @PostMapping(value = "/logout")
    public void logout(HttpSession session) {
        session.setAttribute("employeeId", null);
    }
}
