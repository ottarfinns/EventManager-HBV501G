package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.EventRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Services.EventService;
import is.hi.hbv501g.eventmanager.Services.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private final EventRepository repository;

    public EventServiceImpl(EventRepository repo) {
        repository = repo;
    }

    /*
     * Takes in a request param of type CreateEventUserRequest
     * Returns the id of the successfully created event
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
        Event e = Mapper.MapCreateEvent(request); //laga MapCreateEvent
        // call repository.save
        s = repository.save(e);
        return s;
    }
}
