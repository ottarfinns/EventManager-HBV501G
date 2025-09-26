package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Attendee;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateAccountRequest;

/* Provides mapping from request objects to Entity objects with static methods */
public class Mapper {
    public static Attendee MapCreateAttendee(CreateUpdateAccountRequest request) {
        return new Attendee(
                    request.username,
                    request.name,
                    request.email,
                    request.phoneNumber,
                    request.password
            );
    }

    public static Organizer MapCreateOrganizer(CreateUpdateAccountRequest request) {
        return new Organizer(
                request.username,
                request.name,
                request.email,
                request.phoneNumber,
                request.password
        );
    }
}
