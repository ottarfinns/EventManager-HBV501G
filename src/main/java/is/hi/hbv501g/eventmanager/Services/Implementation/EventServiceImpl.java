package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.EventRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.EventFilterRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import is.hi.hbv501g.eventmanager.Services.EventService;
import is.hi.hbv501g.eventmanager.Utilities.Mapper;
import is.hi.hbv501g.eventmanager.Services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static is.hi.hbv501g.eventmanager.Utilities.EventSpecs.*;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final AccountService accountService;
    private final TicketService ticketService;

    public EventServiceImpl(EventRepository repo, AccountService accountService, TicketService ticketService)
    {
        eventRepository = repo;
        this.accountService = accountService;
        this.ticketService = ticketService;
    }

    public Event createEvent(Integer organizerId, CreateUpdateEventRequest request) {
        //Validate request
        if (request == null
                || request.getName() == null
                || request.getName().isEmpty()
                || request.getPrice() == null
                || request.getMaxPeople() == null
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        try {
            // throws if not found
            var organizer = accountService.getOrganizerById(organizerId);Event s;

            Event e = Mapper.Map(request, organizer);
            s = eventRepository.save(e);
            return s;
        }
        catch(Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "User is not an organizer"
            );
        }
    }

    @Override
    public List<Event> getEvents(EventFilterRequest request, boolean getActiveOnly) {
        if (request == null) {
            return eventRepository.findAll(onlyActive(getActiveOnly));
        } else {
            var spec = hasName(request.getName())
                    .and(isType(request.getType()))
                    .and(dateAfter(request.getDateFrom()))
                    .and(dateBefore(request.getDateTo()))
                    .and(hasMaxOccupancy(request.getMaxOccupancy()))
                    .and(priceGreaterThan(request.getMinPrice()))
                    .and(priceLesserThan(request.getMaxPrice()))
                    .and(hasOrganizerId(request.getOrganizerId()))
                    .and(onlyActive(getActiveOnly));
            var venue = request.getVenue();
            if (venue != null) {
                spec = spec.and(hasVenueAddress(venue.getAddress()))
                        .and(hasVenuePostCode(venue.getPostCode()))
                        .and(hasVenueName(venue.getVenueName()));
            }
            return eventRepository.findAll(spec);
        }
    }

    @Override
    public Event updateEvent(Integer eventId, Integer organizerId, CreateUpdateEventRequest request) {
        //Validate request
        if (request == null || eventId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required event data"); // Bad request

        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isEmpty()) throw new RuntimeException();
        else {
            var e = optionalEvent.get();

            // check organizer id is the same
            if (!Objects.equals(e.getOrganizer().getId(), organizerId)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Organizer does not own this event");
            else  {
                if (request.getName() != null) e.setName(request.getName());
                if (request.getType() != null) e.setType(request.getType());
                if (request.getDescription() != null) e.setDescription(request.getDescription());
                if (request.getDateTime() != null) e.setDateTime(request.getDateTime());
                if (request.getMaxPeople() != null) e.setMaxPeople(request.getMaxPeople());
                if (request.getPrice() != null) e.setPrice(request.getPrice());
                if (request.getVenue() != null) {
                    var v = request.getVenue();
                    if (v.getAddress() != null) e.getVenue().setAddress(v.getAddress());
                    if (v.getPostCode() != null) e.getVenue().setPostCode(v.getPostCode());
                    if (v.getVenueName() != null) e.getVenue().setVenueName(v.getVenueName());
                }
                eventRepository.save(e);
                return e;
            }
        }

    }

    public Event getEventById(int id) {
        return eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event with ID " + id + " not found"));
    }

    public Event cancelEvent(Integer eventId, Integer organizerId) {
        if (eventId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing event id");
        if (organizerId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing organizer id");

        //get the event
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event with id " + eventId + " not found");
        Event e = optionalEvent.get();

        //checks that organizer is owner
        if (!Objects.equals(e.getOrganizer().getId(), organizerId)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Organizer does not own this event");


        //cancel the event if it's active
        if (e.isActive()) {
            ticketService.cancelAllEvent(eventId);
            e.setActive(false);
            eventRepository.save(e);
        } else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event is already cancelled");
        }
        return e;
    }


    public void deleteEvent(Integer eventId, Integer organizerId) {
        if (eventId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing event id");
        if (organizerId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing organizer id");

        //find event
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event with id " + eventId + " not found");
        Event e = optionalEvent.get();

        //checks that organizer is owner
        if (!Objects.equals(e.getOrganizer().getId(), organizerId)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Organizer does not own this event");

        //cancel all tickets for event
        if (e.isActive()) {
           ticketService.cancelAllEvent(eventId);
        }

        eventRepository.delete(e);

    }
}
