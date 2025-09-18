package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repo) {
        repository = repo;
    }
}
