package edu.miu.cs.cs544.repository;

import edu.miu.common.repository.BaseRepository;
import edu.miu.cs.cs544.domain.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends BaseRepository<Event,Integer> {
    @Query(value="SELECT COUNT(att.attendance_id) AS attendancecount " +
            "FROM event e " +
            "JOIN schedule AS sc ON sc.event_id = e.event_id " +
            "JOIN session AS ss ON ss.schedule_id = sc.schedule_id " +
            "JOIN attendance AS att on att.sid = ss.session_id " +
            "WHERE e.event_id =:eventId" , nativeQuery = true)
    Long getTotalEventAttendanace(@Param("eventId")Long  eventId);


    @Query(value="SELECT COUNT(att.attendance_id) AS attendancecount " +
            "FROM event e " +
            "JOIN schedule AS sc ON sc.event_id = e.event_id " +
            "JOIN session AS ss ON ss.schedule_id = sc.schedule_id " +
            "JOIN attendance AS att on att.sid = ss.session_id " +
            "WHERE e.event_id =:eventId and att.member_id = :memberId " , nativeQuery = true)
    Long getTotalAttendanceByEventAndMember(@Param("eventId")Integer  eventId, @Param("memberId")Long  memberId);
}
