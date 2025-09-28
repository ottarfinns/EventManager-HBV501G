package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Repositories.AttendeeRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.OrganizerRepository;
import is.hi.hbv501g.eventmanager.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private final AttendeeRepository attendeeRepository;
    @Autowired
    private final OrganizerRepository organizerRepository;

    public LoginServiceImpl(AttendeeRepository attendeeRepository, OrganizerRepository organizerRepository) {
        this.attendeeRepository = attendeeRepository;
        this.organizerRepository = organizerRepository;
    }

    public Integer login(String username, String password) {
        return attendeeRepository.findByUsername(username)
                .filter(attendee -> attendee.getPassword().equals(password))
                .map(a -> a.getId())
                .orElse(0);
    }

    /*
    Two login endpoints, one for organizer login and one for attendee login
    api/auth/organizer/login
    api/auth/attendee/login

    We get username and password
    Look for username in db
    If exists then compare corresponding hashed password to hashed password in db
     */
}
