package br.com.challenge.employee_checkin.repositories;

import br.com.challenge.employee_checkin.models.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeesRepository extends JpaRepository<Employees, Long> {

    Optional<Employees> findByEmail(String email);
}
