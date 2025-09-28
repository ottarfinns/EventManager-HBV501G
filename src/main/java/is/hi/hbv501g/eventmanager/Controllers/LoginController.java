package is.hi.hbv501g.eventmanager.Controllers;

import is.hi.hbv501g.eventmanager.Requests.LoginRequest;
import is.hi.hbv501g.eventmanager.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/attendee/login")
    public Integer attendeeLogin(@RequestBody LoginRequest request) {
        return loginService.login(request.getUsername(), request.getPassword());
    }

    @GetMapping("/organizer/login")
    public Integer organizerLogin(@RequestBody LoginRequest request) {
        return loginService.login(request.getUsername(), request.getPassword());
    }

}
