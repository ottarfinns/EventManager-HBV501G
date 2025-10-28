package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateTicketRequest;
import is.hi.hbv501g.eventmanager.Services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
/**
 * Spring REST API Controller, handles operations on tickets.
 */
@RestController
public class TicketController {
    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }


    /**
     * Ticket Creation endpoint
     * User must be authenticated as an attendee.
     * @param request The CreateUpdateTicketRequest for ticket creation.
     * @param userId The id of the authenticated user.
     * @return The successfully created ticket
     */
    @PostMapping("/tickets")
    @PreAuthorize("hasRole('ATTENDEE')")
    public Ticket createTicket(@RequestBody CreateUpdateTicketRequest request, @AuthenticationPrincipal int userId) {
        request.setAttendeeId(userId);
        Ticket ticket = service.createTicket(request);
        if (ticket == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ticket could not be created");
        }
        return ticket;
    }

    /**
     * Get Tickets endpoint
     * User must be authenticated as an attendee.
     * @param userId The id of the authenticated user.
     * @return The tickets of the currently authenticated user.
     */
    @GetMapping("/tickets")
    @PreAuthorize("hasRole('ATTENDEE')")
    public List<Ticket> getTicketByUserId( @AuthenticationPrincipal int userId){
        return service.getTicketByUserId(userId);
    }
    /**
     * Ticket Update endpoint
     * User must be authenticated as an attendee.
     * @param ticketId The id of the ticket to be updated.
     * @param request The CreateUpdateTicketRequest for ticket updating.
     * @param userId The id of the authenticated user.
     * @return The successfully updated ticket
     */
    @PutMapping("/tickets/{ticketId}")
    @PreAuthorize("hasRole('ATTENDEE')")
    public Ticket updateTicketStatus(@AuthenticationPrincipal int userId, @RequestBody CreateUpdateTicketRequest request, @PathVariable int ticketId) {
        if (request.getStatus()== Ticket.Status.cancelled) {
            return service.cancelTicket(userId, ticketId, request);
        } else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can only update ticket status to cancelled");
        }
    }
}
