package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Persistence.Entities.User;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring REST API Controller, handles operations on user accounts.
 */
@RestController
@Controller
public class AccountController {
    @Autowired
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    /**
     * Account Creation endpoint
     * @param request The CreateUpdateUserRequest for account creation.
     * @return The successfully created user account
    */
    @PostMapping("/accounts")
    public User createAccount(@RequestBody CreateUpdateUserRequest request) {
        return service.createAccount(request);
    }

    /* Update endpoint
    * Takes in a request param of type CreateUpdateUserRequest
    * Returns the id of the successfully updated user account
    */

    /* Delete Endpoint
    * Takes in the account id
    */

    /* Get endpoint
    * Takes in the account id
    */
}