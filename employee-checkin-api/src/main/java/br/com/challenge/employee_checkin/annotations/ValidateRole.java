package br.com.challenge.employee_checkin.annotations;

import br.com.challenge.employee_checkin.enums.RoleEnum;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateRole {
    RoleEnum[] value();
}