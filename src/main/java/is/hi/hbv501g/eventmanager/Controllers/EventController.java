package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.EventFilterRequest;
import is.hi.hbv501g.eventmanager.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/events/{id}")
    public Event getEventById(@PathVariable int id) {
        return service.getEventById(id);
    }


    @PostMapping("/events")
    //@PreAuthorize("isAuthenticated()")
    public Event createEvent(@RequestBody CreateUpdateEventRequest request) {
        return service.createEvent(request);
    }

    @GetMapping("/test-auth")
//    @PreAuthorize("isAuthenticated()")
    public String testAuthentication(@AuthenticationPrincipal Integer userId) {
        return "Authentication success! Your user ID is: " + userId;
    }

    @GetMapping("/test-auth/organizer")
    @PreAuthorize("hasRole('ORGANIZER')")
    public String testOrganizerOnly(@AuthenticationPrincipal Integer userId) {
        return "Success! You are an ORGANIZER with user ID: " + userId;
    }

    @GetMapping("/test-auth/attendee")
    @PreAuthorize("hasRole('ATTENDEE')")
    public String testAttendeeOnly(@AuthenticationPrincipal Integer userId) {
        return "Success! You are an ATTENDEE with user ID: " + userId;
    }


    @PutMapping("/events/{eventId}")
    public Optional<Event> updateEvents(@PathVariable Integer eventId, @RequestBody CreateUpdateEventRequest request) {
        return service.updateEvent(eventId, request);
    }
}
