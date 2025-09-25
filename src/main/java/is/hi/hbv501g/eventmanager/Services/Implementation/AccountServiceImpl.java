package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Repositories.AttendeeRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateAccountRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private final AttendeeRepository repository;

    public AccountServiceImpl(AttendeeRepository repo) {
        repository = repo;
    }

    public Integer createAccount(CreateUpdateAccountRequest request){
        return 0;
    }
    public Integer updateAccount(CreateUpdateAccountRequest request){
        return 0;
    }
}
