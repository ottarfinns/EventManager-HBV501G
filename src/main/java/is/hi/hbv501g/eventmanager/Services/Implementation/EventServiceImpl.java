package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Repositories.EventRepository;
import is.hi.hbv501g.eventmanager.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private final EventRepository repository;

    public EventServiceImpl(EventRepository repo) {
        repository = repo;
    }
}
