package is.hi.hbv501g.eventmanager.Services;


import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;

public interface AccountService {
    public Integer createAccount(CreateUpdateUserRequest request);
    public Integer updateAccount(CreateUpdateUserRequest request);
}
