package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Attendee;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import is.hi.hbv501g.eventmanager.Persistence.Entities.User;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;

public interface AccountService {
    /**
     * Creates a User account
     * @param request The account information.
     * @return The newly created account
     */
    User createAccount(CreateUpdateUserRequest request);

    /**
     * Update a User account.
     * @param request The account information.
     * @return The updated account.
     */
    User updateAccount(CreateUpdateUserRequest request);

    /**Get an Organizer by the id*/
    Organizer getOrganizerById(Integer organizerId);
    /**Get an Attendee by the id*/
    Attendee getAttendeeById(Integer attendeeId);

    /** Delete attendee account*/
    void deleteAttendee (Integer attendeeId);

    /** Delete organizer account*/
    void deleteOrganizer (Integer organizerId);
}
