package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
* Controller using Spring. Handles APIs for user accounts
 */
@RestController
@Controller
public class AccountController {
    @Autowired
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    /* Create endpoint
    * Takes in a request param of type CreateUpdateUserRequest
    * Returns the id of the successfully created user account
    */
    @PostMapping("/accounts")
    public Integer createAccount(@RequestBody CreateUpdateUserRequest request) {
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