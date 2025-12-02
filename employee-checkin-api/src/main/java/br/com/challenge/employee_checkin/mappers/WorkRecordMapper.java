package br.com.challenge.employee_checkin.mappers;

import br.com.challenge.employee_checkin.dtos.WorkRecord;
import br.com.challenge.employee_checkin.models.WorkRecords;

public class WorkRecordMapper {

    public static WorkRecord toDTO(WorkRecords record) {
        if (record == null) return null;
        return new WorkRecord(record.getCheckInTime(), record.getCheckOutTime());
    }
}
