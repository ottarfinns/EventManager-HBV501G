package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Attendee;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.AttendeeRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateAccountRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private final AttendeeRepository attendeeRepository;

    public AccountServiceImpl(AttendeeRepository repo) {
        attendeeRepository = repo;
    }

    public Integer createAccount(CreateUpdateAccountRequest request){
        // Mapping to Attendee entity. Do logic.
        //
        Attendee attendee = new Attendee(
                request.username, request.name, request.email, request.phoneNumber
        );

        // call repository.save
        var s = attendeeRepository.save(attendee);

        // return userid
        return s.getId();
    }
    public Integer updateAccount(CreateUpdateAccountRequest request){
        return 0;
    }
}
