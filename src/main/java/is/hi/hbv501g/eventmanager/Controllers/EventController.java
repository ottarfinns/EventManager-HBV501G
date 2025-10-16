package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.EventFilterRequest;
import is.hi.hbv501g.eventmanager.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Controller
public class EventController {
    @Autowired
    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping("/events")
    public List<Event> getEvents(@RequestBody(required = false) EventFilterRequest request) {
        return service.getEvents(request, true);
    }

    @PostMapping("/events")
    //@PreAuthorize("isAuthenticated()")
    public Event createEvent(@RequestBody CreateUpdateEventRequest request) {
        return service.createEvent(request);
    }

    @GetMapping("/test-auth")
    @PreAuthorize("isAuthenticated()")
    public String testAuthentication() {
        return "authentication success";
    }

}
