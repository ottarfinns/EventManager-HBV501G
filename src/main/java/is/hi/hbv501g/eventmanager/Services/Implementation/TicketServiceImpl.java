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
import java.util.Objects;

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
     * Returns the successfully created ticket
     */
    public Ticket createTicket(CreateUpdateTicketRequest request) {

        if (request == null || request.getAttendeeId() == null || request.getAttendeeId() == 0 || request.getEventId() == null || request.getEventId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        //double check event is still active
        Event event = eventRepository.findById(request.getEventId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        if (!event.isActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event is not active");
        }

        //ensure event has space
        int ticketCount = repository.countByEventIdAndStatus(request.getEventId(), Status.active);
        int maxOccupancy = event.getMaxPeople();
        if (ticketCount >= maxOccupancy) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event is full — cannot create more tickets");
        }


        //throw if user already has an active ticket for this event
        List<Ticket> tickets = getTicketByUserId(request.getAttendeeId());
        boolean alreadyHasTicket = tickets.stream()
                .anyMatch(t ->
                        t.getEventId().equals(request.getEventId()) && t.getStatus() == Status.active);
        if (alreadyHasTicket) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has a ticket for this event");
        }

        //create and save ticket to repository
        Ticket s;
        Ticket t = Mapper.Map(request, event.getPrice());

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

    public Ticket cancelTicket(Integer userId, Integer ticketId, CreateUpdateTicketRequest request) {
        //get ticket by id
        if (ticketId==null || ticketId==0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing ticket ID");
        }
        Ticket t = getTicketById(ticketId);
        if (t == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket with ID " + request.getEventId() + " not found");
        }

        //check whether user calling function owns this ticket
        if (!Objects.equals(t.getAttendeeId(), userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Users can only cancel their own tickets");
        }

        //check status, can only cancel active ticket
        if (t.getStatus()==Status.cancelled) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ticket is cancelled already");
        } else if (t.getStatus()==Status.checked_in) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can't cancel used ticket");
        } else if (t.getStatus()==Status.expired) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can't cancel expired ticket");
        }

        t.setStatus(Status.cancelled);
        repository.save(t);
        return t;
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

    public void cancelAllEvent(Integer eventId) {
        if (eventId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing event id");
        }
        List<Ticket> tickets = getTicketByEventId(eventId);
        if (tickets == null) {
            return; //no tickets for event
        }
        for (Ticket t : tickets) {
            if (t.getStatus()==Status.active) {
                t.setStatus(Status.cancelled);
            }
        }
        repository.saveAll(tickets);

    }

}
