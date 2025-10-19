package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket.Status;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.EventRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.TicketRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateTicketRequest;
import is.hi.hbv501g.eventmanager.Services.Mapper;
import is.hi.hbv501g.eventmanager.Services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private final TicketRepository repository;
    private final EventRepository eventRepository;

    public TicketServiceImpl(TicketRepository repo, EventRepository eventRepo) {
        repository = repo;
        eventRepository = eventRepo;
    }

    /*
     * Takes in a request param of type CreateUpdateTicketRequest
     * Returns the id of the successfully created ticket
     */
    public Ticket createTicket(CreateUpdateTicketRequest request) {

        if (request == null || request.attendeeId == null || request.attendeeId == 0 || request.price == null || request.eventId == null || request.eventId == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        if (request.price < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price has to be greater or equal 0");
        }


        //ensure event has space
        Event event = eventRepository.findById(request.eventId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        int ticketCount = repository.countByEventId(request.eventId);
        int maxOccupancy = event.getMaxPeople();
        if (ticketCount >= maxOccupancy) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event is full — cannot create more tickets");
        }


        //return null if user already has a ticket for this event
        List<Ticket> tickets = getTicketByUserId(request.attendeeId);
        boolean alreadyHasTicket = tickets.stream()
                .anyMatch(t -> t.getEventId().equals(request.eventId));
        if (alreadyHasTicket) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has a ticket for this event");
        }

        Ticket s;
        Ticket t = Mapper.MapCreateTicket(request);
        //call repository.save
        s = repository.save(t);
        return s;
    }

    public Ticket getTicketById(Integer ticketId) {
        return repository.findById(ticketId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket with ID " + ticketId + " not found"));
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
