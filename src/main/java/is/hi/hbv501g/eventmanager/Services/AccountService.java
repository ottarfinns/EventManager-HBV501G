package is.hi.hbv501g.eventmanager.Services;


import is.hi.hbv501g.eventmanager.Requests.CreateUpdateAccountRequest;

public interface AccountService {
    public Integer createAccount(CreateUpdateAccountRequest request);
    public Integer updateAccount(CreateUpdateAccountRequest request);
}
