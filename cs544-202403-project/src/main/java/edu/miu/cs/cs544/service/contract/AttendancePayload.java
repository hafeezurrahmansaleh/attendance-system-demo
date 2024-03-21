package edu.miu.cs.cs544.service.contract;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class AttendancePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long attendanceId;
    private Long memberId;
    private Long scannerId;
    private Long session;
//    private boolean isPresent;
    private String attendanceDateTIme;
}
