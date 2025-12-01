package br.com.challenge.employee_checkin.controllers;

import br.com.challenge.employee_checkin.dto.LoginRequest;
import br.com.challenge.employee_checkin.dto.LoginResponse;
import br.com.challenge.employee_checkin.services.EmployeesService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private EmployeesService employeesService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpSession session) {
        LoginResponse loginResponse = employeesService.login(loginRequest, session);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        employeesService.logout(session);
        return ResponseEntity.noContent().build();
    }
}
