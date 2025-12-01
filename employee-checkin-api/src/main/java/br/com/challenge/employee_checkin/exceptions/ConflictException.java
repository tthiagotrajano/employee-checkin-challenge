package br.com.challenge.employee_checkin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CheckException extends RuntimeException {
    public CheckException(String message) {
        super(message);
    }
}
