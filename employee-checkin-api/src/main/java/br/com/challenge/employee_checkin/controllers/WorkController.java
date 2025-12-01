package br.com.challenge.employee_checkin.controllers;

import br.com.challenge.employee_checkin.dto.CheckResponse;
import br.com.challenge.employee_checkin.models.WorkRecords;
import br.com.challenge.employee_checkin.services.WorkService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private WorkService workService;

    @PostMapping(value = "/checkin")
    public CheckResponse checkIn(HttpSession session) {
        return workService.checkIn(session);
    }

    @PostMapping(value = "/checkout")
    public CheckResponse checkOut(HttpSession session) {
        return workService.checkOut(session);
    }

    @GetMapping(value = "/list")
    public Page<WorkRecords> getWorkRecords(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startTime = startDate != null ? LocalDateTime.parse(startDate, formatter) : null;
        LocalDateTime endTime = endDate != null ? LocalDateTime.parse(endDate, formatter) : null;

        Pageable pageable = PageRequest.of(page, size);

        return workService.getWorkRecords(name, startTime, endTime, pageable);
    }
}
