package is.hi.hbv501g.eventmanager.Services;

public interface LoginService {
    /**
     * Login for attendees.
     * @return The attendee id.
     */
    Integer attendeeLogin(String username, String password);
    /**
     * Login for organizers.
     * @return The organizer id.
     */
    Integer organizerLogin(String username, String password);
}
