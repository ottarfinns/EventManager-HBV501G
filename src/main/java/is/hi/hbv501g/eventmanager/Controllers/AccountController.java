package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Attendee;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import is.hi.hbv501g.eventmanager.Persistence.Entities.User;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

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

    /**
     * Update Organizers endpoint
     * @param request The CreateUpdateUserRequest for account updating.
     * @return The updated organizer account
    */
    @PutMapping("/accounts/organizer/{userId}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public Organizer updateOrganizer(@PathVariable Integer pathId, @RequestBody CreateUpdateUserRequest request, @AuthenticationPrincipal Integer authId) {
        if (!Objects.equals(pathId, authId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authenticated as this user");
        }

        if (request == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        request.setId(authId);
        request.setIsOrganizer(true);

        User user = service.updateAccount(request);
        if (!(user instanceof Organizer o)) {
            throw new RuntimeException("Error in updating organizer account");
        }

        return o;
    }

    /**
     * Update Attendees endpoint
     * @param request The CreateUpdateUserRequest for account updating.
     * @return The updated attendee account
     */
    @PutMapping("/accounts/attendee/{userId}")
    @PreAuthorize("hasRole('ATTENDEE')")
    public Attendee updateAttendee(@PathVariable Integer pathId, @RequestBody CreateUpdateUserRequest request, @AuthenticationPrincipal Integer authId) {
        if (!Objects.equals(pathId, authId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authenticated as this user");
        }

        if (request == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        request.setId(authId);
        request.setIsOrganizer(false);

        User user = service.updateAccount(request);
        if (!(user instanceof Attendee a)) {
            throw new RuntimeException("Error in updating attendee account");
        }

        return a;

    }

    /* Delete Endpoint
    * Takes in the account id
    */

    /* Get endpoint
    * Takes in the account id
    */
}