package edu.miu.cs.cs544.service.contract;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class SchedulePayLoad implements Serializable {
    private Integer scheduleId;
    private Date startDate;
    private Date endDate;
}
