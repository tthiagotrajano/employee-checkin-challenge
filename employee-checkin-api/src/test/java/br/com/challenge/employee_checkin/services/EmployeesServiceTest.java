package br.com.challenge.employee_checkin.services;

import br.com.challenge.employee_checkin.dtos.LoginRequest;
import br.com.challenge.employee_checkin.dtos.LoginResponse;
import br.com.challenge.employee_checkin.enums.RoleEnum;
import br.com.challenge.employee_checkin.exceptions.ConflictException;
import br.com.challenge.employee_checkin.exceptions.NotFoundException;
import br.com.challenge.employee_checkin.models.Employees;
import br.com.challenge.employee_checkin.repositories.EmployeesRepository;
import br.com.challenge.employee_checkin.services.impl.EmployeesService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class EmployeesServiceTest {

    @Mock
    private EmployeesRepository employeesRepository;

    @Mock
    private HttpSession httpSession;

    @InjectMocks
    private EmployeesService employeesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginSuccess() {
        Employees mockEmployee = new Employees();
        mockEmployee.setId(1L);
        mockEmployee.setName("Employee 01");
        mockEmployee.setEmail("employee01@grupomoura.com");
        mockEmployee.setRole(RoleEnum.USER);

        when(employeesRepository.findByEmail(mockEmployee.getEmail())).thenReturn(Optional.of(mockEmployee));

        LoginResponse response = employeesService.login(new LoginRequest(mockEmployee.getEmail(), "123"), httpSession);

        verify(httpSession, times(1)).setAttribute("employeeId", 1L);
        assertNotNull(response);
        assertEquals("Employee 01", response.name());
    }

    @Test
    void loginWrongPassword() {
        Employees mockEmployee = new Employees();
        mockEmployee.setId(1L);
        mockEmployee.setName("Employee 01");
        mockEmployee.setEmail("employee01@grupomoura.com");
        mockEmployee.setRole(RoleEnum.USER);

        when(employeesRepository.findByEmail(mockEmployee.getEmail())).thenReturn(Optional.of(mockEmployee));

        LoginRequest wrongPasswordRequest = new LoginRequest(mockEmployee.getEmail(), "1234");

        Exception exception = assertThrows(NotFoundException.class, () -> {
            employeesService.login(wrongPasswordRequest, httpSession);
        });

        assertEquals("Email or password invalid.", exception.getMessage());
        verify(httpSession, never()).setAttribute(anyString(), any());
    }

    @Test
    void loginEmailWrong() {
        Employees mockEmployee = new Employees();
        mockEmployee.setId(1L);
        mockEmployee.setName("Employee 03");
        mockEmployee.setEmail("employee03@grupomoura.com");
        mockEmployee.setRole(RoleEnum.USER);

        when(employeesRepository.findByEmail(mockEmployee.getEmail())).thenReturn(Optional.empty());

        LoginRequest request = new LoginRequest(mockEmployee.getEmail(), "123");

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            employeesService.login(request, httpSession);
        });

        assertEquals("Email or password invalid.", exception.getMessage());
        verify(httpSession, never()).setAttribute(anyString(), any());
    }

    @Test
    void logoutSuccess() {
        when(httpSession.getAttribute("employeeId")).thenReturn(1L);

        employeesService.logout(httpSession);

        verify(httpSession, times(1)).setAttribute("employeeId", null);
    }

    @Test
    void logoutNotLoggedIn() {
        when(httpSession.getAttribute("employeeId")).thenReturn(null);

        ConflictException exception = assertThrows(ConflictException.class, () -> {
            employeesService.logout(httpSession);
        });

        assertEquals("You are not logged in. Please log in.", exception.getMessage());
    }
}