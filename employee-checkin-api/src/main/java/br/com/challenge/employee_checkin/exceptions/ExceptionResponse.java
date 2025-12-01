package br.com.challenge.employee_checkin.exceptions;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}