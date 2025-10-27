package is.hi.hbv501g.eventmanager.Services;


import is.hi.hbv501g.eventmanager.Persistence.Entities.Attendee;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import is.hi.hbv501g.eventmanager.Persistence.Entities.User;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;

public interface AccountService {
    User createAccount(CreateUpdateUserRequest request);
    User updateAccount(CreateUpdateUserRequest request);
    Organizer getOrganizerById(Integer organizerId);
    Attendee getAttendeeById(Integer attendeeId);
}
