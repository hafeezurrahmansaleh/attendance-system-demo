package edu.miu.cs.cs544.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Event")
@Setter
@Getter
@NoArgsConstructor
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private List<Schedule> scheduleLis= new ArrayList<>();
    @ManyToMany(mappedBy = "events")
    private List<Member> memberList  =new ArrayList<>();
    public Event(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
