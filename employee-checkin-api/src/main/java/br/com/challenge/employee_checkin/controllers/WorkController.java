package br.com.challenge.employee_checkin.controllers;

import br.com.challenge.employee_checkin.controllers.docs.WorkControllerDocs;
import br.com.challenge.employee_checkin.dtos.CheckResponse;
import br.com.challenge.employee_checkin.dtos.WorkRecordReport;
import br.com.challenge.employee_checkin.services.impl.WorkService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/work")
public class WorkController implements WorkControllerDocs {

    @Autowired
    private WorkService workService;

    @Override
    @PostMapping(value = "/checkin")
    public CheckResponse checkIn(@RequestHeader("Authorization") String tokenHeader) {
        return workService.checkIn(tokenHeader);
    }

    @Override
    @PostMapping(value = "/checkout")
    public CheckResponse checkOut(@RequestHeader("Authorization") String tokenHeader) {
        return workService.checkOut(tokenHeader);
    }

    @Override
    @GetMapping(value = "/list")
    public Page<WorkRecordReport> getWorkRecords(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader("Authorization") String tokenHeader
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startTime = startDate != null ? LocalDateTime.parse(startDate, formatter) : null;
        LocalDateTime endTime = endDate != null ? LocalDateTime.parse(endDate, formatter) : null;

        Pageable pageable = PageRequest.of(page, size);

        return workService.getWorkRecords(name, startTime, endTime, pageable, tokenHeader);
    }
}
