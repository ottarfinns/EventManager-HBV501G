package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;

public interface EventService {
     Event createEvent(CreateUpdateEventRequest request);
}
