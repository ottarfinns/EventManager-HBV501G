package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Attendee;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import is.hi.hbv501g.eventmanager.Persistence.Entities.User;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.AttendeeRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.OrganizerRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import is.hi.hbv501g.eventmanager.Services.TicketService;
import is.hi.hbv501g.eventmanager.Utilities.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private final AttendeeRepository attendeeRepository;
    @Autowired
    private final OrganizerRepository organizerRepository;

    private final TicketService ticketService;

    private final String emailRegex = "^(.+)@(\\S+)$";

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public AccountServiceImpl(AttendeeRepository aRepo, OrganizerRepository oRepo, TicketService ticketService) {
        attendeeRepository = aRepo;
        organizerRepository = oRepo;
        this.ticketService = ticketService;
    }

    public User createAccount(CreateUpdateUserRequest request) {
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

            if (organizerRepository.findByUsername(request.getUsername()).isPresent() // check if usename taken
                    || attendeeRepository.findByUsername(request.getUsername()).isPresent()
                    || organizerRepository.findByEmail(request.getEmail()).isPresent() // check if email used
            ) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
            }
            // Map
            Organizer o = Mapper.MapOrganizer(request);
            // Password hashing
            o.setPassword(encoder.encode(request.getPassword()));
            // call repository.save
            s = organizerRepository.save(o);

        } else {
            // Attendee

            if (attendeeRepository.findByUsername(request.getUsername()).isPresent() // check if username taken
                    || attendeeRepository.findByEmail(request.getEmail()).isPresent() // check if email taken
                    || organizerRepository.findByUsername(request.getUsername()).isPresent()
            ) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
            }

            // Map
            Attendee a = Mapper.MapAttendee(request);
            // Password hashing
            a.setPassword(encoder.encode(request.getPassword()));
            // call repository.save
            s = attendeeRepository.save(a);

        }

        // return userid
        return s;
    }

    public User updateAccount(CreateUpdateUserRequest request) {
        // Validate request
        // if nothing is to be updated, throw
        if (request.getName() == null
                && request.getPassword() == null
                && request.getEmail() == null
                && request.getUsername() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing fields");
        }

        if (request.getEmail() != null && !Pattern.matches(emailRegex, request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }
        if (request.getPassword() != null && request.getPassword().length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 4 characters");
        }
        if (request.getUsername() != null && (
                attendeeRepository.findByUsername(request.getUsername()).isPresent()
                        || organizerRepository.findByUsername(request.getUsername()).isPresent()
        )) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username taken");
        }

        // update nonnull parameters and save to repository
        if (request.getIsOrganizer()) {
            // Organizer

            if (organizerRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email taken");
            }

            Organizer s = organizerRepository.findById(request.getId()).orElseThrow();
            if (request.getUsername() != null) s.setUsername(request.getUsername());
            if (request.getName() != null) s.setName(request.getName());
            if (request.getEmail() != null) s.setEmail(request.getEmail());
            if (request.getPhoneNumber() != null) s.setPhoneNumber(request.getPhoneNumber());
            if (request.getPassword() != null) s.setPassword(encoder.encode(request.getPassword()));

            organizerRepository.save(s);
            return s;
        } else {
            // Attendee

            if (attendeeRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email taken");
            }

            Attendee s = attendeeRepository.findById(request.getId()).orElseThrow();
            if (request.getUsername() != null) s.setUsername(request.getUsername());
            if (request.getName() != null) s.setName(request.getName());
            if (request.getEmail() != null) s.setEmail(request.getEmail());
            if (request.getPhoneNumber() != null) s.setPhoneNumber(request.getPhoneNumber());
            if (request.getPassword() != null) s.setPassword(encoder.encode(request.getPassword()));
            attendeeRepository.save(s);
            return s;
        }

    }

    /**
     * Get an Organizer by the id
     *
     * @param organizerId The attendee id.
     * @return Organizer if he was found, otherwise null.
     */
    public Organizer getOrganizerById(Integer organizerId) {
        return organizerRepository.findById(organizerId).orElse(null);
    }

    /**
     * Get an Attendee by the id
     *
     * @param attendeeId The attendee id.
     * @return Attendee if he was found, otherwise null.
     */
    public Attendee getAttendeeById(Integer attendeeId) {
        return attendeeRepository.findById(attendeeId).orElse(null);
    }


    /**
     * Deletes attendee account from the
     *
     * @param attendeeId the id of the attendee
     */
    public void deleteAttendee(Integer attendeeId) {
        if (attendeeId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing attendee id");

        Optional<Attendee> optionalAttendee = attendeeRepository.findById(attendeeId);
        if (optionalAttendee.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee with id " + attendeeId + " not found");
        Attendee a = optionalAttendee.get();

        ticketService.cancelAllUser(attendeeId);

        attendeeRepository.delete(a);
    }

    /**
     * Deletes organizer account
     *
     * @param organizerId the id of the organizer
     */
    public void deleteOrganizer(Integer organizerId) {
        if (organizerId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing organizer id");

        Optional<Organizer> optionalOrganizer = organizerRepository.findById(organizerId);
        if (optionalOrganizer.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizer with id " + organizerId + " not found");
        Organizer o = optionalOrganizer.get();

        organizerRepository.delete(o);
    }
}


