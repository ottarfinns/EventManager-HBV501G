package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket.Status;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.TicketRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateTicketRequest;
import is.hi.hbv501g.eventmanager.Services.Mapper;
import is.hi.hbv501g.eventmanager.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
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
        //Validate request
        if (request == null
        || request.attendeeId == null
        || request.eventId == null) {
            return null; //Failure state
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
