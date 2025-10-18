package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Persistence.Entities.*;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateTicketRequest;
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

    public static Event MapCreateEvent(CreateUpdateEventRequest request, Organizer organizer) {
        return new Event(
                request.name,
                request.type,
                request.description,
                request.dateTime,
                request.maxPeople,
                request.price,
                request.venue,
                organizer,
                true
        );
    }

    public static Ticket MapCreateTicket(CreateUpdateTicketRequest request) {
        return new Ticket(
                request.attendeeId,
                request.eventId,
                request.price,
                Ticket.Status.active
        );
    }
}
