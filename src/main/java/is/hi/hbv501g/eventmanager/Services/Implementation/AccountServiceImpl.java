package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Attendee;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import is.hi.hbv501g.eventmanager.Persistence.Entities.User;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.AttendeeRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.OrganizerRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import is.hi.hbv501g.eventmanager.Services.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Pattern;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private final AttendeeRepository attendeeRepository;
    @Autowired
    private final OrganizerRepository organizerRepository;

    private final String emailRegex = "^(.+)@(\\S+)$";

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public AccountServiceImpl(AttendeeRepository aRepo, OrganizerRepository oRepo) {
        attendeeRepository = aRepo; organizerRepository = oRepo;
    }

    /*
     * Takes in a request param of type CreateUpdateUserRequest
     * Returns the id of the successfully created user account
     */
    public User createAccount(CreateUpdateUserRequest request){
        // Validate request - check nulls, validate parameters
        if (request == null
                || request.getUsername() == null
                || request.getName() == null
                || request.getPassword() == null
                || request.getEmail() == null
                || request.getIsOrganizer() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }
        if (!Pattern.matches(emailRegex, request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }
        if (request.getPassword().length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 4 characters");
        }

        User s;
        if (request.getIsOrganizer()) {
            // Organizer

            if( organizerRepository.findByUsername(request.getUsername()).isPresent() // check if usename taken
                    || attendeeRepository.findByUsername(request.getUsername()).isPresent()
                    || organizerRepository.findByEmail(request.getEmail()).isPresent() // check if email used
            ) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
            }
            // Map
            Organizer o = Mapper.MapCreateOrganizer(request);
            // Password hashing
            o.setPassword(encoder.encode(request.getPassword()));
            // call repository.save
            s = organizerRepository.save(o);

        } else {
            // Attendee

            if( attendeeRepository.findByUsername(request.getUsername()).isPresent() // check if username taken
                    || attendeeRepository.findByEmail(request.getEmail()).isPresent() // check if email taken
                    || organizerRepository.findByUsername(request.getUsername()).isPresent()
            ) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
            }

            // Map
            Attendee a = Mapper.MapCreateAttendee(request);
            // Password hashing
            a.setPassword(encoder.encode(request.getPassword()));
            // call repository.save
            s = attendeeRepository.save(a);

        }

        // return userid
        return s;
    }

    /** Not Implemented **/
    public User updateAccount(CreateUpdateUserRequest request){
        // Follow the same pattern as createAccount.
        // When validating, nulls are ok as that means we just don´t update those parameters.
        // Assume id is set, use that to look up the user. Don´t let users update isOrganizer.
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);

    }

    public Organizer getOrganizerById(Integer organizerId) {
        var optionalOrganizer = organizerRepository.findById(organizerId);if (optionalOrganizer.isPresent()) {
            return optionalOrganizer.get();
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User with ID " + organizerId + " not found"
            );
        }
    }
    public Attendee getAttendeeById(Integer attendeeId) {
        var optionalAttendee = attendeeRepository.findById(attendeeId);if (optionalAttendee.isPresent()) {
            return optionalAttendee.get();
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User with ID " + attendeeId + " not found"
            );
        }
    }
}
