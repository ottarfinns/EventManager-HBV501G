package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class TicketController {
    @Autowired
    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }
}
