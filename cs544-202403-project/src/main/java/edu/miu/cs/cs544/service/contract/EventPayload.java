package edu.miu.cs.cs544.service.contract;

import edu.miu.cs.cs544.domain.Schedule;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class EventPayload implements Serializable {
    private Integer eventId;
    private String name;
    private String description;
    private List<Schedule> scheduleLis= new ArrayList<>();
}
