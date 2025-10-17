package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.EventFilterRequest;

import java.util.List;

public interface EventService {
     Event createEvent(Integer organizerId, CreateUpdateEventRequest request);
     List<Event> getEvents(EventFilterRequest request, boolean getActiveOnly);
     Event updateEvent(Integer eventId, Integer organizerId, CreateUpdateEventRequest request);
     Event getEventById(int id);
}
