package br.com.challenge.employee_checkin.aspects;

import br.com.challenge.employee_checkin.annotations.ValidateRole;
import br.com.challenge.employee_checkin.enums.RoleEnum;
import br.com.challenge.employee_checkin.exceptions.UnauthorizedException;
import br.com.challenge.employee_checkin.models.Employees;
import br.com.challenge.employee_checkin.repositories.EmployeesRepository;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ValidateRoleAspect {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private HttpSession httpSession;

    @Around("@annotation(validateRole)")
    public Object checkRole(ProceedingJoinPoint joinPoint, ValidateRole validateRole) throws Throwable {

        Long employeeId = (Long) httpSession.getAttribute("employeeId");

        Employees user = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new UnauthorizedException("Access denied. Please login."));

        RoleEnum[] allowedRoles = validateRole.value();

        if (!Arrays.asList(allowedRoles).contains(user.getRole())) {
            throw new UnauthorizedException("The user does not have permission to access this resource.");
        }

        return joinPoint.proceed();
    }
}
