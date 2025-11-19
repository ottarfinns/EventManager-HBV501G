package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Utilities.EventType;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Venue;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.EventFilterRequest;
import is.hi.hbv501g.eventmanager.Services.EventService;
import is.hi.hbv501g.eventmanager.Services.ImageService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring REST API Controller, handles operations on events.
 */
@RestController
public class EventController {
    private final EventService service;
    private final ImageService imageService;

    public EventController(EventService service, ImageService imageService) {
        this.service = service;
        this.imageService = imageService;
    }

    /**
     * Retrieves a list of events with optional filtering via query parameters.
     *
     * @param name          Optional. The name of the event to search for.
     * @param type          Optional. The type of event.
     * @param venueName     Optional. The name of the venue.
     * @param venueAddress  Optional. The street address of the venue.
     * @param venuePostCode Optional. The postal code of the venue.
     * @param dateFrom      Optional. The start of the date range to include events from.
     * @param dateTo        Optional. The end of the date range to include events up to.
     * @param maxOccupancy  Optional. The maximum number of attendees allowed for the event.
     * @param minPrice      Optional. The minimum ticket price to filter by.
     * @param maxPrice      Optional. The maximum ticket price to filter by.
     * @param organizerId   Optional. The unique ID of the organizer responsible for the event.
     * @param activeOnly    If {@code true} (default), only active events are returned;
     *                      if {@code false}, both active and inactive events are included.
     * @return The list of events that match the filter.
     */
    @GetMapping("/events")
    public List<Event> getEvents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) EventType type,
            @RequestParam(required = false) String venueName,
            @RequestParam(required = false) String venueAddress,
            @RequestParam(required = false) String venuePostCode,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(required = false) Integer maxOccupancy,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Long organizerId,
            @RequestParam(defaultValue = "true") boolean activeOnly
    ) {
        EventFilterRequest filter = new EventFilterRequest();
        filter.setName(name);
        filter.setType(type);
        filter.setDateFrom(dateFrom);
        filter.setDateTo(dateTo);
        filter.setMaxOccupancy(maxOccupancy);
        filter.setMinPrice(minPrice);
        filter.setMaxPrice(maxPrice);
        filter.setOrganizerId(organizerId);

        if (venueName != null || venueAddress != null || venuePostCode != null) {
            Venue venue = new Venue();
            venue.setVenueName(venueName);
            venue.setAddress(venueAddress);
            venue.setPostCode(venuePostCode);
            filter.setVenue(venue);
        }

        boolean noFilter = filter.getName() == null
                && filter.getType() == null
                && filter.getVenue() == null
                && filter.getDateFrom() == null
                && filter.getDateTo() == null
                && filter.getMaxOccupancy() == null
                && filter.getMinPrice() == null
                && filter.getMaxPrice() == null
                && filter.getOrganizerId() == null;

        if (noFilter && activeOnly) {
            return service.getEvents(null, true);
        }

        return service.getEvents(filter, activeOnly);
    }


    /**
     * Get Event endpoint.
     *
     * @param eventId The id of the event to get.
     * @return The event.
     */
    @GetMapping("/events/{eventId}")
    public Event getEventById(@PathVariable Integer eventId) {
        return service.getEventById(eventId);
    }

    /**
     * Event Creation endpoint.
     * User must be authenticated as an organizer.
     *
     * @param request The CreateUpdateEventRequest for event creation.
     * @param userId  The currently authenticated user's id, from request header.
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
    @PutMapping("/events/{eventId}/image")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Integer eventId, @AuthenticationPrincipal Integer userId) throws IOException {
        return imageService.uploadImage(file, eventId, userId);
    }

    /**
     * Get image endpoint.
     *
     * @param eventId The id of the event the image is associated with.
     * @return A ResponseEntity containing a success message if the image was
     * found or an appropriate error message if it was not found.
     * @throws IOException if an I/O error occurs while reading the image bytes.
     */
    @GetMapping("/events/{eventId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer eventId) throws IOException {
        return imageService.findByEventId(eventId);
    }

    /**
     * Event editing endpoint.
     * User must be authenticated as an organizer.
     *
     * @param eventId The id of the event to be updated.
     * @param request The CreateUpdateEventRequest for event editing.
     * @param userId  The currently authenticated user's id, from request header.
     * @return The successfully updated event.
     */
    @PutMapping("/events/{eventId}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public Event updateEvent(@PathVariable Integer eventId, @RequestBody CreateUpdateEventRequest request, @AuthenticationPrincipal Integer userId) {
        if (request.isCancel()) {
            return service.cancelEvent(eventId, userId);
        }
        return service.updateEvent(eventId, userId, request);
    }

    /**
     * Delete event endpoint.
     * User must be authenticated as an organizer.
     *
     * @param eventId The id of the event to be deleted.
     * @param userId  The currently authenticated user's id, from request header.
     */
    @DeleteMapping("/events/{eventId}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer eventId, @AuthenticationPrincipal Integer userId) {
        service.deleteEvent(eventId, userId);
        return ResponseEntity.noContent().build();
    }
}
