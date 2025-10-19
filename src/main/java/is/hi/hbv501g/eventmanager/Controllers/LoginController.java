package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Requests.LoginRequest;
import is.hi.hbv501g.eventmanager.Services.JwtService;
import is.hi.hbv501g.eventmanager.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private final LoginService loginService;

    @Autowired
    private JwtService jwtService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/attendee/login")
    public String attendeeLogin(@RequestBody LoginRequest request) {
        if (request == null || request.getUsername() == null || request.getUsername().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username and password are required");
        }

        Integer userId = loginService.attendeeLogin(request.getUsername(), request.getPassword());

        if (userId == null || userId <= 0) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        return jwtService.generateToken(userId, "ATTENDEE");
    }

    @PostMapping("/organizer/login")
    public String organizerLogin(@RequestBody LoginRequest request) {
        if (request == null || request.getUsername() == null || request.getUsername().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username and password are required");
        }

        Integer userId = loginService.organizerLogin(request.getUsername(), request.getPassword());

        if (userId == null || userId <= 0) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        return jwtService.generateToken(userId, "ORGANIZER");
    }
}
