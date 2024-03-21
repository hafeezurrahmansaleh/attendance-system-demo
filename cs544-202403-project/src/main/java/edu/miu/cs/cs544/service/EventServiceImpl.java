package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.cs.cs544.domain.Event;
import edu.miu.cs.cs544.repository.EventRepository;
import edu.miu.cs.cs544.service.EventService;
import edu.miu.cs.cs544.service.contract.EventPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl extends BaseReadWriteServiceImpl<EventPayload,Event,Integer> implements EventService {
    @Autowired
    EventRepository eventRepository;
    @Override
    public Long getTotalEventAttendanace(Long eventId) {
        return eventRepository.getTotalEventAttendanace(eventId);
    }
}
