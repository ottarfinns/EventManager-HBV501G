package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Venue;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.EventRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.VenueRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.EventFilterRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import is.hi.hbv501g.eventmanager.Services.EventService;
import is.hi.hbv501g.eventmanager.Services.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import static is.hi.hbv501g.eventmanager.Persistence.Repositories.EventSpecs.*;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private final EventRepository eventRepository;
    @Autowired
    private final VenueRepository venueRepository;
    @Autowired
    private final AccountService accountService;

    public EventServiceImpl(EventRepository repo, VenueRepository venueRepo, AccountService accountService)
    {
        eventRepository = repo;
        this.venueRepository = venueRepo;
        this.accountService = accountService;
    }

    /*
     * Takes in a request param of type CreateEventUserRequest, returns created User
     */
    public Event createEvent(Integer organizerId, CreateUpdateEventRequest request) {
        //Validate request
        if (request == null
                || request.name == null
                || request.name.isEmpty()
                || request.price == null
        ) {
            return null; //Failure state
        }

        // find venue
        Optional<Venue> v = Optional.empty();
        if (request.venueId != null && request.venueId != 0)
        {
            v = venueRepository.findById(request.venueId);
        }

        // find organizer
        var o = (Organizer) accountService.getAccountById(organizerId);

        Event s;
        Event e = Mapper.MapCreateEvent(request, v, o);
        // call repository.save
        s = eventRepository.save(e);
        return s;
    }

    @Override
    public List<Event> getEvents(EventFilterRequest request, boolean getActiveOnly) {
        if (request == null) {
            return eventRepository.findAll(onlyActive(getActiveOnly));
        } else {
            return eventRepository.findAll(
                    hasName(request.getName())
                            .and(isType(request.getType()))
                            .and(hasVenueId(request.getVenueId()))
                            .and(dateAfter(request.getDateFrom()))
                            .and(dateBefore(request.getDateTo()))
                            .and(hasMaxOccupancy(request.getMaxOccupancy()))
                            .and(priceGreaterThan(request.getMinPrice()))
                            .and(priceLesserThan(request.getMaxPrice()))
                            .and(hasOrganizerId(request.getOrganizerId()))
            );
        }
    }

    @Override
    public Optional<Event> updateEvent(Integer eventId, Integer organizerId, CreateUpdateEventRequest request) {
        //Validate request
        if (request == null || eventId == null) return Optional.empty(); // Bad request

        // get venue if needed
        Optional<Venue> optionalVenue;
        if (request.venueId != null) optionalVenue = venueRepository.findById(request.venueId);
        else {
            optionalVenue = Optional.empty();
        }

        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        optionalEvent.ifPresent(e -> {
            if (request.name != null) e.setName(request.name);
            if (request.type != null) e.setType(request.type);
            if (request.description != null) e.setDescription(request.description);
            if (request.dateTime != null) e.setDateTime(request.dateTime);
            if (request.maxPeople != null) e.setMaxPeople(request.maxPeople);
            if (request.price != null) e.setPrice(request.price);
            optionalVenue.ifPresent(e::setVenue);
            eventRepository.save(e);
        });
        return optionalEvent;
    }

    public Event getEventById(int id) {
        return eventRepository.findById(id).orElse(null);
    }
}
