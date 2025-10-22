package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Persistence.Entities.*;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateTicketRequest;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;

/* Provides mapping from request objects to Entity objects with static methods */
public class Mapper {
    public static Attendee MapCreateAttendee(CreateUpdateUserRequest request) {
        return new Attendee(
                request.getUsername(),
                request.getName(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getPassword()
            );
    }

    public static Organizer MapCreateOrganizer(CreateUpdateUserRequest request) {
        return new Organizer(
                request.getUsername(),
                request.getName(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getPassword()
        );
    }

    public static Event MapCreateEvent(CreateUpdateEventRequest request, Organizer organizer) {
        return new Event(
                request.getName(),
                request.getType(),
                request.getDescription(),
                request.getDateTime(),
                request.getMaxPeople(),
                request.getPrice(),
                request.getVenue(),
                organizer,
                true
        );
    }

    public static Ticket MapCreateTicket(CreateUpdateTicketRequest request, Double eventPrice) {
        return new Ticket(
                request.getAttendeeId(),
                request.getEventId(),
                eventPrice,
                Ticket.Status.active
        );
    }
}
