package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Repositories.AttendeeRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.OrganizerRepository;
import is.hi.hbv501g.eventmanager.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private final AttendeeRepository attendeeRepository;
    @Autowired
    private final OrganizerRepository organizerRepository;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public LoginServiceImpl(AttendeeRepository attendeeRepository, OrganizerRepository organizerRepository) {
        this.attendeeRepository = attendeeRepository;
        this.organizerRepository = organizerRepository;
    }

    public Integer attendeeLogin(String username, String rawPassword) {
        return attendeeRepository.findByUsername(username)
                .filter(attendee -> encoder.matches(rawPassword, attendee.getPassword()))
                .map(a -> a.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid username or password"
                ));
    }

    public Integer organizerLogin(String username, String rawPassword) {
        return organizerRepository.findByUsername(username)
                .filter(organizer -> encoder.matches(rawPassword, organizer.getPassword()))
                .map(o -> o.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid username or password"
                ));
    }
}
