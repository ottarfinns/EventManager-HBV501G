package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.EventRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.EventFilterRequest;
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
    private final EventRepository repository;

    public EventServiceImpl(EventRepository repo) {
        repository = repo;
    }

    /*
     * Takes in a request param of type CreateEventUserRequest, returns created User
     */
    public Event createEvent(CreateUpdateEventRequest request) {
        //Validate request
        if (request == null
                || request.name == null
                || request.name.isEmpty()
                || request.price == null
                || request.venue == null
        ) {
            return null; //Failure state
        }
        Event s;
        Event e = Mapper.MapCreateEvent(request);
        // call repository.save
        s = repository.save(e);
        return s;
    }

    @Override
    public List<Event> getEvents(EventFilterRequest request, boolean getActiveOnly) {
        if (request == null) {
            return repository.findAll(onlyActive(getActiveOnly));
        } else {
            return repository.findAll(
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
    public Optional<Event> updateEvent(Integer eventId, CreateUpdateEventRequest request) {
        //Validate request
        if (request == null || eventId == null) return null; // Bad request

        Optional<Event> optionalEvent = repository.findById(eventId);
        optionalEvent.ifPresent(e -> {
            if (request.name != null) e.setName(request.name);
            if (request.type != null) e.setType(request.type);
            if (request.description != null) e.setDescription(request.description);
            if (request.dateTime != null) e.setDateTime(request.dateTime);
            if (request.maxPeople != null) e.setMaxPeople(request.maxPeople);
            if (request.price != null) e.setPrice(request.price);
            if (request.venue != null) e.setVenue(request.venue);
            if (request.organizer != null) e.setOrganizer(request.organizer);
            repository.save(e);
        });
        return optionalEvent;
    }

    public Event getEventById(int id) {
        return repository.findById(id).orElse(null);
    }
}
