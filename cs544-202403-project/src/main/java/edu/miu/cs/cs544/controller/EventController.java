package edu.miu.cs.cs544.controller;

import edu.miu.common.controller.BaseReadWriteController;
import edu.miu.cs.cs544.domain.Event;
import edu.miu.cs.cs544.service.EventService;
import edu.miu.cs.cs544.service.contract.EventPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController extends BaseReadWriteController<EventPayload,Event,Integer> {
    @Autowired
    EventService eventService;
    @GetMapping("{eventId}/attendance")
    public  Long getTotalEventAttendanace(@PathVariable("eventId") Long eventId){

        return eventService.getTotalEventAttendanace(eventId);
    }


}
