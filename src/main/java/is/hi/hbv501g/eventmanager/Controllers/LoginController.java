package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Requests.LoginRequest;
import is.hi.hbv501g.eventmanager.Services.JwtService;
import is.hi.hbv501g.eventmanager.Services.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
/**
 * Spring REST API Controller, handles authentication and login.
 */
@RestController
@RequestMapping("/auth")
public class LoginController {
    private final LoginService loginService;
    private final JwtService jwtService;

    public LoginController(LoginService loginService, JwtService jwtService) {
        this.loginService = loginService;
        this.jwtService = jwtService;
    }

    /**
     * Login endpoint for attendee accounts
     * @param request The LoginRequest containing username and password.
     * @return A newly issued authToken.
     */
    @PostMapping("/attendee/login")
    public String attendeeLogin(@RequestBody LoginRequest request) {
        if (request == null || request.getUsername() == null || request.getUsername().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username and password are required");
        }

        Integer userId = loginService.attendeeLogin(request.getUsername(), request.getPassword());

        return jwtService.generateToken(userId, "ATTENDEE");
    }

    /**
     * Login endpoint for organizer accounts
     * @param request The LoginRequest containing username and password.
     * @return A newly issued authToken.
     */
    @PostMapping("/organizer/login")
    public String organizerLogin(@RequestBody LoginRequest request) {
        if (request == null || request.getUsername() == null || request.getUsername().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username and password are required");
        }

        Integer userId = loginService.organizerLogin(request.getUsername(), request.getPassword());

        return jwtService.generateToken(userId, "ORGANIZER");
    }
}
