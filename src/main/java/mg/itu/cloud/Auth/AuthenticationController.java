package mg.itu.cloud.Auth;

import mg.itu.cloud.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        if (!userService.checkIfEmailExists(loginRequest.getEmail())) {
            return "Vous n'etes pas inscrit";
        }
        return authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest.getEmail(), registerRequest.getNom(), registerRequest.getPassword());
    }

    @GetMapping("/validate-email/{token}")
    public String validateEmail(@PathVariable String token) {
        return authenticationService.validateEmail(token);
    }
}
