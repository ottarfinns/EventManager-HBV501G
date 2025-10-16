package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket.Status;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.TicketRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateTicketRequest;
import is.hi.hbv501g.eventmanager.Services.Mapper;
import is.hi.hbv501g.eventmanager.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private final TicketRepository repository;

    public TicketServiceImpl(TicketRepository repo) {
        repository = repo;
    }

    /*
     * Takes in a request param of type CreateUpdateTicketRequest
     * Returns the id of the successfully created ticket
     */
    public Ticket createTicket(CreateUpdateTicketRequest request) {

        if (request == null) {
            //log.warn("createTicket: request is null");
            return null;
        }

        if (request.attendeeId == null || request.attendeeId == 0) {
            //log.warn("createTicket: invalid attendeeId={}", request.attendeeId);
            return null;
        }

        if (request.eventId == null || request.eventId == 0) {
            //log.warn("createTicket: invalid eventId={}", request.eventId);
            return null;
        }

        if (request.price == null || request.price < 0) {
            //log.warn("createTicket: invalid price={}", request.price);
            return null;
        }

        //tékka hvort það sé nú þegar til miði fyrir þessu eventi fyrir þennan attendee
        List<Ticket> tickets = getTicketByUserId(request.attendeeId);
        boolean alreadyHasTicket = tickets.stream()
                .anyMatch(t -> t.getEventId().equals(request.eventId));
        if (alreadyHasTicket) {
            log.warn("Ticket with event id {} already exists for this user", request.eventId);
            return null; //can also return the ticket that matches
        }

        Ticket s;
        Ticket t = Mapper.MapCreateTicket(request);
        //call repository.save
        s = repository.save(t);
        return s;
    }

    public Ticket getTicketById(Integer ticketId) {
        return repository.findById(ticketId).orElse(null);
    }

    public List<Ticket> getTicketByUserId(Integer attendeeId) {
        return repository.findByAttendeeId(attendeeId);
    }

    public List<Ticket> getTicketByEventId(Integer eventId) {
        return repository.findByEventId(eventId);
    }

    public boolean cancelTicket(Integer ticketId) {
        Ticket t = getTicketById(ticketId);
        if (t == null) {
            return false;
        }
        t.setStatus(Status.cancelled);
        repository.save(t);
        return true;
    }

    public boolean cancelAllUser(Integer attendeeId) {
        if (attendeeId == null) {
            return false;
        }
        List<Ticket> tickets = getTicketByUserId(attendeeId);

        if (tickets == null) {
            return false;
        }

        for (Ticket t : tickets) {
            t.setStatus(Status.cancelled);
        }
        repository.saveAll(tickets);
        return true;
    }

    public boolean cancelAllEvent(Integer eventId) {
        if (eventId == null) {
            return false;
        }
        List<Ticket> tickets = getTicketByEventId(eventId);
        if (tickets == null) {
            return false;
        }
        for (Ticket t : tickets) {
            t.setStatus(Status.cancelled);
        }
        repository.saveAll(tickets);
        return true;

    }

}
