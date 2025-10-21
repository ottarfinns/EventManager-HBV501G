package is.hi.hbv501g.eventmanager.Services;


import is.hi.hbv501g.eventmanager.Persistence.Entities.User;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;

public interface AccountService {
    Integer createAccount(CreateUpdateUserRequest request);
    Integer updateAccount(CreateUpdateUserRequest request);
    User getAccountById(Integer userId);
}
