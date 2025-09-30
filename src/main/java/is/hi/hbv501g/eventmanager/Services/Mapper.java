package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Attendee;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;

/* Provides mapping from request objects to Entity objects with static methods */
public class Mapper {
    public static Attendee MapCreateAttendee(CreateUpdateUserRequest request) {
        return new Attendee(
                request.username,
                request.name,
                request.email,
                request.phoneNumber,
                request.password
            );
    }

    public static Organizer MapCreateOrganizer(CreateUpdateUserRequest request) {
        return new Organizer(
                request.username,
                request.name,
                request.email,
                request.phoneNumber,
                request.password
        );
    }

    public static Event MapCreateEvent(CreateUpdateEventRequest request) {
        return new Event(
                request.name,
                request.type,
                request.description,
                request.dateTime,
                request.maxPeople,
                request.price,
                request.venue,
                request.organizer,
                true
        );
    }
}
