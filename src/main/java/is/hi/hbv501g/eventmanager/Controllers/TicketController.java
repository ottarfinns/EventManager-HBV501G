package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateTicketRequest;
import is.hi.hbv501g.eventmanager.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/ticket")
    public Ticket createTicket(@RequestBody CreateUpdateTicketRequest request) {
        return service.createTicket(request);
    }


    @GetMapping("/attendee/{id}/tickets")
    public List<Ticket> getTicketByUserId(@PathVariable int pathId, @AuthenticationPrincipal int userId){
        if(pathId != userId){
            return null;
        }
        return service.getTicketByUserId(userId);
    }
}
