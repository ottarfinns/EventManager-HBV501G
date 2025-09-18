package is.hi.hbv501g.eventmanager.Controllers.Implementation;

import is.hi.hbv501g.eventmanager.Controllers.AccountController;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

// Controller using Spring. Handles APIs for user accounts
@RestController
@Controller
public class AccountControllerImpl implements AccountController {
    private final AccountService service;

    public AccountControllerImpl(AccountService service) {
        this.service = service;
    }

    /* Create endpoint
     * Takes in a request param of type CreateUpdateUserRequest
     * Returns the id of the successfully created user account
     */

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
