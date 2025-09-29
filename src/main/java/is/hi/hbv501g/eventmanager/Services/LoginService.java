package is.hi.hbv501g.eventmanager.Services;

public interface LoginService {
    Integer attendeeLogin(String username, String password);
    Integer organizerLogin(String username, String password);
}
