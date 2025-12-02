package br.com.challenge.employee_checkin.annotations;

import br.com.challenge.employee_checkin.enums.RoleEnum;
import br.com.challenge.employee_checkin.exceptions.UnauthorizedException;
import br.com.challenge.employee_checkin.models.Employees;
import br.com.challenge.employee_checkin.repositories.EmployeesRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();

        Long employeeId = (Long) request.getAttribute("employeeId");

        Employees employee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new UnauthorizedException("Access denied. Please login."));

        RoleEnum[] allowedRoles = validateRole.value();

        if (!Arrays.asList(allowedRoles).contains(employee.getRole())) {
            throw new UnauthorizedException("The user does not have permission to access this resource.");
        }

        return joinPoint.proceed();
    }
}
