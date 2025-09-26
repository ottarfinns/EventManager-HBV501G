package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Attendee;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
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
}
