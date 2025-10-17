package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateTicketRequest;
import is.hi.hbv501g.eventmanager.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
