package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class EventController {
    @Autowired
    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }
}
