package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.EventFilterRequest;
import is.hi.hbv501g.eventmanager.Services.EventService;
import is.hi.hbv501g.eventmanager.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@Controller
public class EventController {
    @Autowired
    private final EventService service;
    @Autowired
    private final ImageService imageService;

    public EventController(EventService service, ImageService imageService) {
        this.service = service;
        this.imageService = imageService;
    }

    @GetMapping("/events")
    public List<Event> getEvents(@RequestBody(required = false) EventFilterRequest request) {
        return service.getEvents(request, true);
    }

    @GetMapping("/events/{id}")
    public Event getEventById(@PathVariable int id) {
        Event event = service.getEventById(id);
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Event with id %s not found", id));
        }
        return event;
    }


    @PostMapping("/events")
    @PreAuthorize("hasRole('ORGANIZER')")
    public Event createEvent(@RequestBody CreateUpdateEventRequest request, @AuthenticationPrincipal Integer userId) {
        return service.createEvent(userId, request);
    }

    @PostMapping("/events/{eventId}/image")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable int eventId) throws IOException {
        return imageService.uploadImage(file, eventId);

    }
    @GetMapping("/events/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) throws IOException {
        return imageService.findById(id);
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
    @PreAuthorize("hasRole('ORGANIZER')")
    public Event updateEvents(@PathVariable Integer eventId, @RequestBody CreateUpdateEventRequest request, @AuthenticationPrincipal Integer userId) {
        return service.updateEvent(eventId, userId, request);
    }
}
