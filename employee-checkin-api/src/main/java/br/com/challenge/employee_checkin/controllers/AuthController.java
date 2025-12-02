package br.com.challenge.employee_checkin.controllers;

import br.com.challenge.employee_checkin.controllers.docs.AuthControllerDocs;
import br.com.challenge.employee_checkin.dtos.LoginRequest;
import br.com.challenge.employee_checkin.dtos.LoginResponse;
import br.com.challenge.employee_checkin.services.impl.EmployeesService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

    @Autowired
    private EmployeesService employeesService;

    @Override
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpSession session) {
        LoginResponse loginResponse = employeesService.login(loginRequest, session);
        return ResponseEntity.ok(loginResponse);
    }

    @Override
    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        employeesService.logout(session);
        return ResponseEntity.noContent().build();
    }
}
