package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Repositories.AttendeeRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.OrganizerRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl {
    private final AttendeeRepository attendeeRepository;
    private final OrganizerRepository organizerRepository;

    public LoginServiceImpl(AttendeeRepository attendeeRepository, OrganizerRepository organizerRepository) {
        this.attendeeRepository = attendeeRepository;
        this.organizerRepository = organizerRepository;
    }
}
