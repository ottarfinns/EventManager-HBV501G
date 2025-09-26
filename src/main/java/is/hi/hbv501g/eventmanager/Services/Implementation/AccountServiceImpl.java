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
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private final AttendeeRepository attendeeRepository;
    @Autowired
    private final OrganizerRepository organizerRepository;

    private final String emailRegex = "^(.+)@(\\S+)$";

    public AccountServiceImpl(AttendeeRepository aRepo, OrganizerRepository oRepo) {
        attendeeRepository = aRepo; organizerRepository = oRepo;
    }

    /*
     * Takes in a request param of type CreateUpdateUserRequest
     * Returns the id of the successfully created user account
     */
    public Integer createAccount(CreateUpdateUserRequest request){
        // Validate request - check nulls, validate parameters
        if (request == null
                || request.username == null
                || request.name == null
                || request.password == null
                || request.email == null
                || request.isOrganizer == null
                || !Pattern.matches(emailRegex,request.email) // check if email pattern matches
                || request.password.length() < 4 // Check password length
        ) {
            return 0; // Failure state
        }

        User s;
        if (request.isOrganizer) {
            // Organizer

            if( !organizerRepository.findByUsername(request.username).isEmpty() // check if usename taken
                    || !organizerRepository.findByEmail(request.email).isEmpty() // check if email used
            ) {
                return 0;
            }
            // Map
            Organizer o = Mapper.MapCreateOrganizer(request);
            // call repository.save
            s = organizerRepository.save(o);

        } else {
            // Attendee

//            if( !attendeeRepository.findByUsername(request.username).isEmpty() // check if username taken
//                    || !attendeeRepository.findByEmail(request.email).isEmpty() // check if email taken
//            ) {
//                return 0;
//            }

            // Map
            Attendee a = Mapper.MapCreateAttendee(request);
            // call repository.save
            s = attendeeRepository.save(a);

        }

        // return userid
        return s.getId();
    }

    public Integer updateAccount(CreateUpdateUserRequest request){
        // Follow the same pattern as createAccount.
        // When validating, nulls are ok as that means we just don´t update those parameters.
        // Assume id is set, use that to look up the user. Don´t let users update isOrganizer.
        return 0;

    }
}
