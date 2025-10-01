package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;

import java.util.List;

public interface EventService {
     Event createEvent(CreateUpdateEventRequest request);
     List<Event> getEvents();
}
