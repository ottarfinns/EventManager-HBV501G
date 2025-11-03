package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Attendee;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import is.hi.hbv501g.eventmanager.Persistence.Entities.User;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Objects;

/**
 * Spring REST API Controller, handles operations on user accounts.
 */
@RestController
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
    public Organizer updateOrganizer(@PathVariable Integer userId, @RequestBody CreateUpdateUserRequest request, @AuthenticationPrincipal Integer authId) {
        if (!Objects.equals(userId, authId)) {
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
    public Attendee updateAttendee(@PathVariable Integer userId, @RequestBody CreateUpdateUserRequest request, @AuthenticationPrincipal Integer authId) {
        if (!Objects.equals(userId, authId)) {
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

    /**
     * Deletes Attendee endpoint. Must be authenticated to delete an endpoint.
     * @param authId The authentication id for the current user.
     * @param userId The path id.
     */
    @DeleteMapping("/accounts/attendee/{userId}")
    @PreAuthorize("hasRole('ATTENDEE')")
    public void deleteAttendee(@PathVariable Integer userId, @AuthenticationPrincipal Integer authId) {
        if (!Objects.equals(userId, authId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authenticated as this user");
        }
        service.deleteAttendee(authId);
    }

    /**
     * Deletes organizer endpoint. Must be authenticated to delete an endpoint.
     * @param authId The authentication id for the current user.
     * @param userId The path id.
     */
    @DeleteMapping("/accounts/organizer/{userId}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public void deleteOrganizer(@PathVariable Integer userId, @AuthenticationPrincipal Integer authId) {
        if (!Objects.equals(userId, authId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authenticated as this user");
        }
        service.deleteOrganizer(authId);
    }

    /**
     * Gets Attendee endpoint.
     * @param userId The path id.
     * @return attendee, throws an error if the attendee does not exist.
     */
    @GetMapping("/accounts/attendee/{userId}")
    public Attendee getAttendeeById(@PathVariable Integer userId) {
        Attendee attendee = service.getAttendeeById(userId);
        if (attendee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Attendee with id %s not found", userId));
        }
        return attendee;
    }

    /**
     * Gets Organizer endpoint.
     * @param userId The path id.
     * @return organizer, throws an error if the attendee does not exist.
     */
    @GetMapping("/accounts/organizer/{userId}")
    public Organizer getOrganizerById(@PathVariable Integer userId) {
        Organizer organizer = service.getOrganizerById(userId);
        if (organizer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Organizer with id %s not found", userId));
        }
        return organizer;
    }

    /* Delete Endpoint
    * Takes in the account id
    */

    /* Get endpoint
    * Takes in the account id
    */
}
