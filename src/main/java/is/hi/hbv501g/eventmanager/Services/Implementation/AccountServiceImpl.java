package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Attendee;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import is.hi.hbv501g.eventmanager.Persistence.Entities.User;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.AttendeeRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.OrganizerRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateAccountRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import is.hi.hbv501g.eventmanager.Services.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private final AttendeeRepository attendeeRepository;
    @Autowired
    private final OrganizerRepository organizerRepository;

    public AccountServiceImpl(AttendeeRepository aRepo, OrganizerRepository oRepo) {
        attendeeRepository = aRepo; organizerRepository = oRepo;
    }

    /*
     * Takes in a request param of type CreateUpdateUserRequest
     * Returns the id of the successfully created user account
     */
    public Integer createAccount(CreateUpdateAccountRequest request){
        // Validate request - logic
        if (request == null) {
            return 0;
        }
        // Validate Password

        User s;
        if (request.isOrganizer) {
            // Map
            Organizer o = Mapper.MapCreateOrganizer(request);
            // call repository.save
            s = organizerRepository.save(o);

        } else {
            // Map
            Attendee a = Mapper.MapCreateAttendee(request);
            // call repository.save
            s = attendeeRepository.save(a);

        }

        // return userid
        return s.getId();
    }

    public Integer updateAccount(CreateUpdateAccountRequest request){
        return 0;
    }
}
