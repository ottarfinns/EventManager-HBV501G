package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.EventFilterRequest;

import java.util.List;

public interface EventService {
     /**
      * Creates an Event, validates and saves.
      * @param organizerId The id of the organizer creating the event.
      * @param request The event information.
      * @return The newly created event.
      */
     Event createEvent(Integer organizerId, CreateUpdateEventRequest request);

     /**
      * Get all events, or get filtered events if specified.
      * @param request The filters to be used. If null, gets all events.
      * @param getActiveOnly If true, get only active events.
      * @return All events, or the filtered events
      */
     List<Event> getEvents(EventFilterRequest request, boolean getActiveOnly);

     /**
      * Updates an Event, validates and saves.
      * @param organizerId The id of the organizer updating the event.
      *                    Validated to ensure that it is the organizer who created the event.
      * @param request The event information.
      * @return The updated event.
      */
     Event updateEvent(Integer eventId, Integer organizerId, CreateUpdateEventRequest request);

     /**
      * Get an Event by its id.
      */
     Event getEventById(int id);

     /**
      * Cancels an Event and all associated tickets
      * @param organizerId The id of the organizer cancelling the event.
      *                    Validated to ensure that it is the organizer who created the event.
      * @return The cancelled event.
      */
     Event cancelEvent(Integer eventId, Integer organizerId);

     /**
      * Deletes an Event and cancels associated tickets
      * @param organizerId The id of the organizer deleting the event.
      *                    Validated to ensure that it is the organizer who created the event.
      */
     void deleteEvent(Integer eventId, Integer organizerId);

     /**
      * Deletes every Event that the Organizer made.
      * @param organizerId The id of the organizer deleting the event.
      *                    Validated to ensure that it is the organizer who created the event.
      */
     void deleteAllUserEvents(Integer organizerId);
}
