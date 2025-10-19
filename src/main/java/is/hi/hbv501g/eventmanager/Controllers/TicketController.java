package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateTicketRequest;
import is.hi.hbv501g.eventmanager.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RestController
public class TicketController {
    @Autowired
    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    /* Create endpoint
     * Takes in a request param of type CreateUpdateTicketRequest
     * Returns the successfully created ticket
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


    @GetMapping("/tickets")
    @PreAuthorize("hasRole('ATTENDEE')")
    public List<Ticket> getTicketByUserId( @AuthenticationPrincipal int userId){
        return service.getTicketByUserId(userId);
    }
}
