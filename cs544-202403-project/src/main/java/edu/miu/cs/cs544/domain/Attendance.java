package edu.miu.cs.cs544.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Attendance")
@Data
public class Attendance implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer attendanceId;

    @ManyToOne
    @JoinColumn(name = "sid")
    private Session session;
    private boolean isPresent;
    @ManyToOne
    @JoinColumn(name = "scanner_id",referencedColumnName ="id")
    private Scanner scanner;
    @Column(name="attendance_datetime")
    private LocalDateTime attendanceDateTIme;
}


