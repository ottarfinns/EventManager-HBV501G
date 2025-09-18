package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Repositories.AccountRepository;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repo) {
        repository = repo;
    }
}
