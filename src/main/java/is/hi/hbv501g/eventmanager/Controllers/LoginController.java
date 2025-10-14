package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Requests.LoginRequest;
import is.hi.hbv501g.eventmanager.Services.JwtService;
import is.hi.hbv501g.eventmanager.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        Integer userId = loginService.attendeeLogin(request.getUsername(), request.getPassword());
        if (userId > 0) {
            return jwtService.generateToken(userId, "ATTENDEE");
        }
        return null;
    }

    @PostMapping("/organizer/login")
    public String organizerLogin(@RequestBody LoginRequest request) {
        Integer userId = loginService.organizerLogin(request.getUsername(), request.getPassword());
        if (userId > 0) {
            return jwtService.generateToken(userId, "ORGANIZER");
        }
        return null;    }

}
