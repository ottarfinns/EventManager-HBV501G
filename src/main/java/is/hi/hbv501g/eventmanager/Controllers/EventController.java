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
/**
 * Spring REST API Controller, handles operations on events.
 */
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
     * Image upload endpoint.
     *
     * @param file    The image file to be uploaded.
     * @param eventId The Id of the event the image is associated with.
     * @param userId  The Id of the organizer attempting the upload.
     * @return A ResponseEntity containing a success message if the image was
     * succesfully uploaded or an appropriate error message if the
     * upload fails.
     * @throws IOException if an I/O error occurs while reading the image bytes.
     */
    @PostMapping("/events/{eventId}/image")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable int eventId, @AuthenticationPrincipal Integer userId) throws IOException {
        return imageService.uploadImage(file, eventId, userId);
    }

    /**
     * Get image endpoint.
     *
     * @param eventId The id of the event the image is associated with.
     * @return A ResponseEntity containing a success message if the image was
     *         found or an appropriate error message if it was not found.
     * @throws IOException if an I/O error occurs while reading the image bytes.
     */
    @GetMapping("/events/{eventId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int eventId) throws IOException {
        return imageService.findByEventId(eventId);
    }
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
