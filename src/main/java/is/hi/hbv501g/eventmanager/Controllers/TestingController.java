package is.hi.hbv501g.eventmanager.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class TestingController {

    @GetMapping("/test-auth")
    public String testAuthentication(@AuthenticationPrincipal Integer userId) {
        return "Authentication success! Your user ID is: " + userId;
    }

    @GetMapping("/test-auth/organizer")
    @PreAuthorize("hasRole('ORGANIZER')")
    public String testOrganizerOnly(@AuthenticationPrincipal Integer userId) {
        return "Success! You are an ORGANIZER with user ID: " + userId;
    }

    @GetMapping("/test-auth/attendee")
    @PreAuthorize("hasRole('ATTENDEE')")
    public String testAttendeeOnly(@AuthenticationPrincipal Integer userId) {
        return "Success! You are an ATTENDEE with user ID: " + userId;
    }


}
