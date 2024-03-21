package edu.miu.cs.cs544.repository;

import edu.miu.cs.cs544.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EventRepositoryTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    EventRepository repository;
    @Test
    public void countAttendanceByEventAndMember_success() {
        //Given
        Event event = new Event("event name", "description");
        List<Schedule> schedules= new ArrayList<>();
        Schedule schedule = new Schedule(LocalDate.now().minusDays(5), LocalDate.now());
        Session session = new Session();
        session.setStartTime(LocalTime.now().minusHours(1));
        session.setEndTime(LocalTime.now());
        schedule.setSessionList(Arrays.asList(session));
        schedules.add(schedule);
        event.setScheduleLis(schedules);
        event = em.persist(event);

        Attendance attendance = new Attendance();
        attendance.setPresent(true);
        attendance.setSession(session);
        attendance = em.persist(attendance);

        Member member = new Member("firstName", "lastName", "emailAddress", "barcode");
        member.setAttendances(Arrays.asList(attendance));
        member = em.persist(member);
        em.flush();

        //when
        Long rs = repository.getTotalAttendanceByEventAndMember(event.getEventId(), member.getId());

        //then
        assertEquals(1, rs);
    }

    @Test
    public void countAttendanceByEventId_success() {
        //Given
        Event event = new Event("event name", "description");
        List<Schedule> schedules= new ArrayList<>();
        Schedule schedule = new Schedule(LocalDate.now().minusDays(5), LocalDate.now());
        Session session = new Session();
        session.setStartTime(LocalTime.now().minusHours(2));
        session.setEndTime(LocalTime.now().minusHours(1));

        Session session1 = new Session();
        session1.setStartTime(LocalTime.now().minusHours(1));
        session1.setEndTime(LocalTime.now());

        schedule.setSessionList(Arrays.asList(session, session1));
        schedules.add(schedule);
        event.setScheduleLis(schedules);
        event = em.persist(event);

        Attendance attendance = new Attendance();
        attendance.setPresent(true);
        attendance.setSession(session);
        attendance = em.persist(attendance);

        Attendance attendance1 = new Attendance();
        attendance1.setPresent(true);
        attendance1.setSession(session1);
        attendance1 = em.persist(attendance1);

        Member member = new Member("firstName", "lastName", "emailAddress", "barcode");
        member.setAttendances(Arrays.asList(attendance, attendance1));
        member = em.persist(member);
        em.flush();

        //when
        Long rs = repository.getTotalEventAttendanace(event.getEventId().longValue());

        //then
        assertEquals(2, rs);
    }

}