package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.EventFilterRequest;
import is.hi.hbv501g.eventmanager.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
/**
 * Spring REST API Controller, handles operations on events.
 */
@RestController
@Controller
public class EventController {
    @Autowired
    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    /**
     * Get Events endpoint.
     * @param request The EventFilterRequest specifying which fields to filter by.
     *                If null, getEvents will get all events.
     * @return A list of all events that fit the filters if there are any.
     */
    @GetMapping("/events")
    public List<Event> getEvents(@RequestBody(required = false) EventFilterRequest request) {
        return service.getEvents(request, true);
    }

    /**
     * Get Event endpoint.
     * @param id The id of the event to get.
     * @return The event.
     */
    @GetMapping("/events/{id}")
    public Event getEventById(@PathVariable int id) {
        Event event = service.getEventById(id);
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Event with id %s not found", id));
        }
        return event;
    }

    /**
     * Event Creation endpoint.
     * User must be authenticated as an organizer.
     * @param request The CreateUpdateEventRequest for event creation.
     * @param userId The currently authenticated user's id, from request header.
     * @return The successfully created event.
     */
    @PostMapping("/events")
    @PreAuthorize("hasRole('ORGANIZER')")
    public Event createEvent(@RequestBody CreateUpdateEventRequest request, @AuthenticationPrincipal Integer userId) {
        return service.createEvent(userId, request);
    }

    /**
     * Event editing endpoint.
     * User must be authenticated as an organizer.
     * @param eventId The id of the event to be updated.
     * @param request The CreateUpdateEventRequest for event editing.
     * @param userId The currently authenticated user's id, from request header.
     * @return The successfully updated event.
     */
    @PutMapping("/events/{eventId}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public Event updateEvents(@PathVariable Integer eventId, @RequestBody CreateUpdateEventRequest request, @AuthenticationPrincipal Integer userId) {
        return service.updateEvent(eventId, userId, request);
    }
}
